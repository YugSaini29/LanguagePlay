package `in`.languageplay.meowguru.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import `in`.languageplay.meowguru.BuildConfig
import java.time.LocalDate

/* ══════════════════ 🎮 QUIZ ══════════════════ */

@Composable
fun QuizScreen(vm: MeowVm, onSpeak: (String, String, Boolean) -> Unit) {
    val quiz by vm.quiz.collectAsState()
    val loading by vm.quizLoading.collectAsState()
    val s by vm.ui.collectAsState()

    var answered by remember(quiz) { mutableStateOf(false) }
    var picked by remember(quiz) { mutableIntStateOf(-1) }
    var earned by remember(quiz) { mutableIntStateOf(0) }

    LaunchedEffect(Unit) { if (quiz == null && !loading) vm.loadQuiz() }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎮 Quiz Mode", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
        Text(
            "⭐ ${s.xp} XP  ·  🎯 Quiz score: ${s.quizScore}",
            fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OrangeM,
            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
        )

        when {
            loading -> {
                Spacer(Modifier.height(40.dp))
                CircularProgressIndicator(color = Sky)
                Text(
                    "Sawal ban raha hai… 🐱", fontSize = 13.sp, color = Muted,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp)
                )
            }

            quiz == null -> {
                Text("😿 Quiz nahi bana — dubara try karo", color = Muted, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.loadQuiz() }) { Text("🔄 Dubara") }
            }

            else -> {
                val q = quiz!!
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            q.question, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold,
                            color = Navy, modifier = Modifier.padding(bottom = 12.dp)
                        )

                        q.options.forEachIndexed { i, opt ->
                            val bg = when {
                                !answered -> MaterialTheme.colorScheme.background
                                i == q.correct -> Color(0xFFE8F9EE)
                                i == picked -> Color(0xFFFDEBEB)
                                else -> MaterialTheme.colorScheme.background
                            }
                            val border = when {
                                !answered -> Sky.copy(alpha = 0.3f)
                                i == q.correct -> GreenM
                                i == picked -> RedM
                                else -> Sky.copy(alpha = 0.2f)
                            }
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = bg,
                                border = androidx.compose.foundation.BorderStroke(2.dp, border),
                                enabled = !answered,
                                onClick = {
                                    if (!answered) {
                                        answered = true
                                        picked = i
                                        val ok = i == q.correct
                                        earned = vm.answerQuiz(ok)
                                        if (ok) onSpeak(q.options[q.correct].t, q.options[q.correct].r, false)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        "${listOf("A", "B", "C", "D")[i]}) ${opt.t}",
                                        fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (opt.r.isNotBlank() && opt.r != opt.t)
                                        Text("🔤 ${opt.r}", fontSize = 11.sp, color = OrangeM,
                                            fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        if (answered) {
                            val ok = picked == q.correct
                            Text(
                                (if (ok) "✅ Sahi! " else "💡 ") + q.explain,
                                fontSize = 12.5.sp, color = Muted, fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                "⭐ +$earned XP",
                                fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Sky,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                            Button(
                                onClick = { vm.loadQuiz() },
                                colors = ButtonDefaults.buttonColors(containerColor = OrangeM),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                            ) { Text("➡️ Agla Sawal", fontWeight = FontWeight.ExtraBold) }
                        }
                    }
                }
            }
        }
    }
}

/* ══════════════════ 🏆 LEADERBOARD ══════════════════ */

@Composable
fun LeaderboardScreen(vm: MeowVm) {
    val lb by vm.lb.collectAsState()
    LaunchedEffect(Unit) { vm.loadLeaderboard() }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("🏆 Leaderboard", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
        lb?.me?.let {
            Text(
                "Aapki rank: #${it.rank} · ⭐${it.xp}",
                fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = OrangeM,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(Modifier.height(12.dp))

        val rows = lb?.top.orEmpty()
        if (rows.isEmpty()) {
            Text(
                "Abhi koi entry nahi — pehle bano! 🚀",
                color = Muted, fontWeight = FontWeight.Bold
            )
        }

        LazyColumn {
            items(rows) { r ->
                val medal = when (r.rank) {
                    1 -> "🥇"; 2 -> "🥈"; 3 -> "🥉"; else -> "#${r.rank}"
                }
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Row(
                        Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(medal, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.width(40.dp))
                        Text(
                            r.name, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,
                            color = Navy, modifier = Modifier.weight(1f), maxLines = 1
                        )
                        Text("⭐${r.xp}", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = OrangeM)
                        Spacer(Modifier.width(8.dp))
                        Text("🔥${r.streak}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RedM)
                    }
                }
            }
        }
    }
}

/* ══════════════════ 📅 CHALLENGE + ⚙️ SETTINGS ══════════════════ */

@Composable
fun SettingsScreen(
    vm: MeowVm,
    days: Set<String>,
    voices: List<String>,
    currentVoice: String,
    onVoicePick: (String) -> Unit,
    onTestVoice: (String) -> Unit,
    onChangeLangs: () -> Unit,
    onOpenBot: () -> Unit,
    dark: Boolean,
    onDark: (Boolean) -> Unit,
    notifOn: Boolean,
    notifTime: String,
    onNotif: (Boolean, String) -> Unit,
    onSignIn: () -> Unit
) {
    val s by vm.ui.collectAsState()

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {

        /* --- profile --- */
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (s.picture.isNotBlank()) {
                        AsyncImage(
                            model = s.picture, contentDescription = null,
                            modifier = Modifier.size(48.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.width(12.dp))
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            if (s.signedIn) "🐱 ${s.name.ifBlank { "Learner" }}" else "🐱 Guest",
                            fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Navy
                        )
                        Text(
                            "⭐ ${s.xp} XP  ·  🔥 ${s.streak} din",
                            fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Muted
                        )
                    }
                    if (s.signedIn) {
                        TextButton(onClick = { vm.signOut() }) {
                            Text("🚪 Logout", color = RedM, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                        }
                    } else {
                        Button(
                            onClick = onSignIn,
                            colors = ButtonDefaults.buttonColors(containerColor = Sky),
                            shape = RoundedCornerShape(20.dp)
                        ) { Text("Sign in", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold) }
                    }
                }
            }
        }

        /* --- 7-day challenge --- */
        item {
            SettingCard("📅 7-Day Challenge") {
                Text(
                    "Lagatar 7 din practice = 🏅 +50 XP bonus!",
                    fontSize = 12.sp, color = Muted, fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (6 downTo 0).forEach { i ->
                        val d = LocalDate.now().minusDays(i.toLong())
                        val done = days.contains(d.toString())
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (done) Color(0xFFE8F9EE) else MaterialTheme.colorScheme.background,
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp, if (done) GreenM else Sky.copy(alpha = 0.25f)
                            ),
                            modifier = Modifier.size(width = 40.dp, height = 48.dp)
                        ) {
                            Column(
                                Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(if (done) "✅" else if (i == 0) "🎯" else "▫️", fontSize = 14.sp)
                                Text(
                                    d.dayOfWeek.name.take(2),
                                    fontSize = 9.sp, fontWeight = FontWeight.ExtraBold,
                                    color = if (done) GreenM else Muted
                                )
                            }
                        }
                    }
                }
            }
        }

        /* --- notifications --- */
        item {
            SettingCard("🔔 Notifications") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Daily practice reminder + streak alerts",
                        fontSize = 12.5.sp, fontWeight = FontWeight.Bold,
                        color = Muted, modifier = Modifier.weight(1f)
                    )
                    Switch(checked = notifOn, onCheckedChange = { onNotif(it, notifTime) })
                }
                if (notifOn) {
                    Spacer(Modifier.height(8.dp))
                    Text("⏰ Reminder ka time", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Muted)
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("09:00", "13:00", "17:00", "19:00", "21:00").forEach { t ->
                            FilterChip(
                                selected = t == notifTime,
                                onClick = { onNotif(true, t) },
                                label = { Text(t, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        }

        /* --- voice --- */
        item {
            SettingCard("🎙️ Meow ki Awaaz") {
                Text(
                    "Male awaaz auto-chuni jati hai. Pasand na aaye toh badlo — 🔊 se sun ke dekho.",
                    fontSize = 11.5.sp, color = Muted, fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                if (voices.isEmpty()) {
                    Text(
                        "😿 Is bhasha ki voice phone me nahi — Settings → Text-to-speech se download karo",
                        fontSize = 11.5.sp, color = RedM, fontWeight = FontWeight.Bold
                    )
                }
                voices.take(6).forEach { v ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = v == currentVoice, onClick = { onVoicePick(v) })
                        Text(
                            v, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1, modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { onTestVoice(v) }) { Text("🔊") }
                    }
                }
            }
        }

        /* --- misc --- */
        item {
            SettingCard("⚙️ Aur") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🌙 Dark mode", fontSize = 13.sp, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    Switch(checked = dark, onCheckedChange = onDark)
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onChangeLangs, modifier = Modifier.fillMaxWidth()) {
                    Text("🌐 Bhasha badlo", fontWeight = FontWeight.ExtraBold)
                }
                Spacer(Modifier.height(6.dp))
                Button(
                    onClick = onOpenBot,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2AABEE)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("✈️ Telegram Bot", fontWeight = FontWeight.ExtraBold) }
            }
        }

        item {
            Text(
                "Made with ❤️ by Rocket Examica × Tech Eagles\nMahakumbrix Innovation · v${BuildConfig.VERSION_NAME}",
                fontSize = 10.sp, color = Muted, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp)
            )
        }
    }
}

@Composable
private fun SettingCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Navy)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}
