package `in`.languageplay.meowguru.notif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import `in`.languageplay.meowguru.MainActivity
import `in`.languageplay.meowguru.R
import `in`.languageplay.meowguru.data.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.first
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

object Notifs {

    const val CH_GENERAL = "meow_general"     // friend pings, push
    const val CH_PRACTICE = "meow_practice"   // daily reminder
    const val CH_STREAK = "meow_streak"       // streak-danger alert

    fun createChannels(ctx: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val nm = ctx.getSystemService(NotificationManager::class.java)
        listOf(
            Triple(CH_GENERAL, "Meow Guru", NotificationManager.IMPORTANCE_DEFAULT),
            Triple(CH_PRACTICE, "Daily Practice", NotificationManager.IMPORTANCE_DEFAULT),
            Triple(CH_STREAK, "Streak Alerts", NotificationManager.IMPORTANCE_HIGH)
        ).forEach { (id, name, imp) ->
            nm.createNotificationChannel(NotificationChannel(id, name, imp))
        }
    }

    fun show(ctx: Context, channel: String, title: String, body: String, id: Int) {
        val pi = PendingIntent.getActivity(
            ctx, id,
            Intent(ctx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val n = NotificationCompat.Builder(ctx, channel)
            .setSmallIcon(R.drawable.ic_notif)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pi)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        runCatching { NotificationManagerCompat.from(ctx).notify(id, n) }
    }

    /** Roz practice reminder + streak-danger check schedule karo */
    fun schedule(ctx: Context, timeHHmm: String) {
        val parts = timeHHmm.split(":")
        val h = parts.getOrNull(0)?.toIntOrNull() ?: 19
        val m = parts.getOrNull(1)?.toIntOrNull() ?: 0

        val now = LocalDateTime.now()
        var next = now.with(LocalTime.of(h, m))
        if (next.isBefore(now)) next = next.plusDays(1)
        val delay = Duration.between(now, next).toMinutes().coerceAtLeast(1)

        val daily = PeriodicWorkRequestBuilder<PracticeWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .build()

        // streak-danger: raat 8:30 — streak tootne se pehle
        var warn = now.with(LocalTime.of(20, 30))
        if (warn.isBefore(now)) warn = warn.plusDays(1)
        val warnDelay = Duration.between(now, warn).toMinutes().coerceAtLeast(1)

        val streak = PeriodicWorkRequestBuilder<StreakWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(warnDelay, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(ctx).apply {
            enqueueUniquePeriodicWork("meow_daily", ExistingPeriodicWorkPolicy.UPDATE, daily)
            enqueueUniquePeriodicWork("meow_streak", ExistingPeriodicWorkPolicy.UPDATE, streak)
        }
    }

    fun cancelAll(ctx: Context) {
        WorkManager.getInstance(ctx).cancelUniqueWork("meow_daily")
        WorkManager.getInstance(ctx).cancelUniqueWork("meow_streak")
    }
}

/** Roz ke time pe: "practice baaki hai" */
class PracticeWorker(ctx: Context, p: WorkerParameters) : CoroutineWorker(ctx, p) {
    override suspend fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        if (!prefs.notifOn.first()) return Result.success()

        val today = java.time.LocalDate.now().toString()
        val done = prefs.days.first().contains(today)
        val streak = prefs.streak()

        if (done) {
            Notifs.show(
                applicationContext, Notifs.CH_PRACTICE,
                "🎉 Aaj ki practice ho gayi!",
                "🔥 $streak din ka streak — kal phir milte hain! 🐱", 101
            )
        } else {
            Notifs.show(
                applicationContext, Notifs.CH_PRACTICE,
                "🐱 Meow tumhara intezaar kar raha hai!",
                "Aaj practice baaki hai — 🔥 $streak din ka streak bachao!", 101
            )
        }
        return Result.success()
    }
}

/** Raat 8:30 — streak khatre me hai */
class StreakWorker(ctx: Context, p: WorkerParameters) : CoroutineWorker(ctx, p) {
    override suspend fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        if (!prefs.notifOn.first()) return Result.success()

        val today = java.time.LocalDate.now().toString()
        val done = prefs.days.first().contains(today)
        val streak = prefs.streak()

        if (!done && streak >= 3) {
            Notifs.show(
                applicationContext, Notifs.CH_STREAK,
                "🚨🔥 STREAK KHATRE ME!",
                "$streak din ka streak aaj raat toot jayega! 2 minute ka quiz bacha lega 🐱", 102
            )
        }
        return Result.success()
    }
}

/** Push notifications — friend messages, admin announcements */
class MeowFcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        // token server ko bhejo (fcm.php) — user_key ke saath
        val prefs = Prefs(applicationContext)
        kotlinx.coroutines.runBlocking {
            val key = prefs.userKey.first()
            if (key.isNotBlank()) {
                runCatching {
                    `in`.languageplay.meowguru.data.Net.api.registerFcm(
                        `in`.languageplay.meowguru.data.FcmReq(user_key = key, token = token)
                    )
                }
            }
        }
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        val title = msg.notification?.title ?: msg.data["title"] ?: "🐱 Meow Guru"
        val body = msg.notification?.body ?: msg.data["body"] ?: ""
        if (body.isNotBlank()) {
            Notifs.show(this, Notifs.CH_GENERAL, title, body, System.currentTimeMillis().toInt())
        }
    }
}
