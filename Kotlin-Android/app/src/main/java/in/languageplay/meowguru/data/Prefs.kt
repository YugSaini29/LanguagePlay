package `in`.languageplay.meowguru.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private val Context.ds by preferencesDataStore("meow")

class Prefs(private val ctx: Context) {

    companion object {
        val USER_KEY = stringPreferencesKey("user_key")
        val SOURCE = stringPreferencesKey("source")
        val TARGET = stringPreferencesKey("target")
        val LEVEL = stringPreferencesKey("level")
        val XP = intPreferencesKey("xp")
        val QUIZ_SCORE = intPreferencesKey("quiz_score")
        val DAYS = stringSetPreferencesKey("days")          // practice dates (ISO)
        val BONUS_DAY = stringPreferencesKey("bonus_day")   // 7-day bonus kab mila
        val PHRASES = stringSetPreferencesKey("phrases")    // "target|roman|meaning"
        val DARK = booleanPreferencesKey("dark")
        val VOICE = stringPreferencesKey("voice")           // chuni hui TTS voice
        val NOTIF_TIME = stringPreferencesKey("notif_time") // "19:00"
        val NOTIF_ON = booleanPreferencesKey("notif_on")
        val NAME = stringPreferencesKey("name")
        val PICTURE = stringPreferencesKey("picture")
    }

    val userKey: Flow<String> = ctx.ds.data.map { it[USER_KEY] ?: "" }
    val source: Flow<String> = ctx.ds.data.map { it[SOURCE] ?: "" }
    val target: Flow<String> = ctx.ds.data.map { it[TARGET] ?: "" }
    val level: Flow<String> = ctx.ds.data.map { it[LEVEL] ?: "beginner" }
    val xp: Flow<Int> = ctx.ds.data.map { it[XP] ?: 0 }
    val dark: Flow<Boolean> = ctx.ds.data.map { it[DARK] ?: false }
    val voice: Flow<String> = ctx.ds.data.map { it[VOICE] ?: "" }
    val notifOn: Flow<Boolean> = ctx.ds.data.map { it[NOTIF_ON] ?: true }
    val notifTime: Flow<String> = ctx.ds.data.map { it[NOTIF_TIME] ?: "19:00" }
    val name: Flow<String> = ctx.ds.data.map { it[NAME] ?: "" }
    val picture: Flow<String> = ctx.ds.data.map { it[PICTURE] ?: "" }
    val phrases: Flow<Set<String>> = ctx.ds.data.map { it[PHRASES] ?: emptySet() }
    val days: Flow<Set<String>> = ctx.ds.data.map { it[DAYS] ?: emptySet() }

    suspend fun <T> set(key: Preferences.Key<T>, v: T) {
        ctx.ds.edit { it[key] = v }
    }

    suspend fun addXp(n: Int): Int {
        var out = 0
        ctx.ds.edit {
            out = (it[XP] ?: 0) + n
            it[XP] = out
        }
        return out
    }

    /** Aaj practice hui — streak/challenge ke liye. Returns: 7-din bonus mila kya */
    suspend fun markPractice(): Boolean {
        val today = LocalDate.now().toString()
        var bonus = false
        ctx.ds.edit { p ->
            val d = (p[DAYS] ?: emptySet()).toMutableSet()
            d.add(today)
            // sirf pichle 30 din rakho
            val cutoff = LocalDate.now().minusDays(30)
            p[DAYS] = d.filter { runCatching { LocalDate.parse(it) >= cutoff }.getOrDefault(false) }.toSet()

            // lagatar 7 din?
            val all = p[DAYS] ?: emptySet()
            val streak7 = (0..6).all { i -> all.contains(LocalDate.now().minusDays(i.toLong()).toString()) }
            if (streak7 && p[BONUS_DAY] != today) {
                p[BONUS_DAY] = today
                p[XP] = (p[XP] ?: 0) + 50
                bonus = true
            }
        }
        return bonus
    }

    /** Lagatar kitne din (aaj se peeche) */
    suspend fun streak(): Int {
        val d = ctx.ds.data.first()[DAYS] ?: return 0
        var n = 0
        var day = LocalDate.now()
        if (!d.contains(day.toString())) day = day.minusDays(1)  // aaj abhi baaki ho sakta hai
        while (d.contains(day.toString())) {
            n++
            day = day.minusDays(1)
        }
        return n
    }

    suspend fun savePhrase(p: String) {
        ctx.ds.edit {
            val s = (it[PHRASES] ?: emptySet()).toMutableSet()
            s.add(p)
            it[PHRASES] = s.toList().takeLast(100).toSet()
        }
    }

    suspend fun removePhrase(p: String) {
        ctx.ds.edit {
            it[PHRASES] = (it[PHRASES] ?: emptySet()).minus(p)
        }
    }

    suspend fun logout() {
        ctx.ds.edit {
            it.remove(USER_KEY); it.remove(NAME); it.remove(PICTURE)
        }
    }
}
