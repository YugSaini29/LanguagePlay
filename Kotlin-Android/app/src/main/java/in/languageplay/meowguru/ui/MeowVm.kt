package `in`.languageplay.meowguru.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import `in`.languageplay.meowguru.auth.GoogleAuth
import `in`.languageplay.meowguru.data.*
import `in`.languageplay.meowguru.notif.Notifs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Chat me ek bubble */
data class Msg(
    val mine: Boolean,
    val text: String = "",              // user ka message
    val speak: String = "",             // target script
    val roman: String = "",
    val source: String = "",            // user ki bhasha me
    val english: String = "",
    val userInTarget: String = "",      // user ka message target me
    val corrected: String = "",
    val tip: String = ""
)

data class UiState(
    val source: String = "",
    val target: String = "",
    val level: String = "beginner",
    val mode: CatMode = CatMode.IDLE,
    val status: String = "",
    val msgs: List<Msg> = emptyList(),
    val xp: Int = 0,
    val streak: Int = 0,
    val quizScore: Int = 0,
    val busy: Boolean = false,
    val name: String = "",
    val picture: String = "",
    val signedIn: Boolean = false
)

class MeowVm(app: Application) : AndroidViewModel(app) {

    private val prefs = Prefs(app)
    private val _ui = MutableStateFlow(UiState())
    val ui = _ui.asStateFlow()

    private var history = mutableListOf<Turn>()
    private val recentQuestions = mutableListOf<String>()   // quiz repeat rokne ke liye

    init {
        viewModelScope.launch {
            _ui.value = _ui.value.copy(
                source = prefs.source.first(),
                target = prefs.target.first(),
                level = prefs.level.first(),
                xp = prefs.xp.first(),
                streak = prefs.streak(),
                name = prefs.name.first(),
                picture = prefs.picture.first(),
                signedIn = prefs.userKey.first().isNotBlank()
            )
        }
    }

    fun setMode(m: CatMode, status: String = "") {
        _ui.value = _ui.value.copy(mode = m, status = status)
    }

    fun setStatus(s: String) {
        _ui.value = _ui.value.copy(status = s)
    }

    /** Language chuni — session shuru */
    fun chooseLangs(source: String, target: String) {
        viewModelScope.launch {
            prefs.set(Prefs.SOURCE, source)
            prefs.set(Prefs.TARGET, target)
            history.clear()
            _ui.value = _ui.value.copy(source = source, target = target, msgs = emptyList())
            send("(session_start)", hidden = true)
        }
    }

    fun setLevel(l: String) {
        viewModelScope.launch {
            prefs.set(Prefs.LEVEL, l)
            _ui.value = _ui.value.copy(level = l)
        }
    }

    /**
     * Chat bhejo. hidden = session_start (bubble nahi dikhega).
     * NOTE: web wala "mode==think toh block" bug yahan nahi — sirf busy flag.
     */
    fun send(text: String, hidden: Boolean = false) {
        val s = _ui.value
        if (s.busy || text.isBlank() || s.target.isBlank()) return

        _ui.value = s.copy(
            busy = true,
            mode = CatMode.THINK,
            status = "🤔 Meow Guru soch raha hai…",
            msgs = if (hidden) s.msgs else s.msgs + Msg(mine = true, text = text)
        )

        viewModelScope.launch {
            runCatching {
                val key = prefs.userKey.first()
                Net.api.chat(
                    ChatReq(
                        user_key = key,
                        target = s.target,
                        source = s.source,
                        level = s.level,
                        history = history.takeLast(10),
                        text = text
                    )
                )
            }.onSuccess { r ->
                if (!r.ok || r.data == null) {
                    _ui.value = _ui.value.copy(
                        busy = false, mode = CatMode.IDLE,
                        status = r.error ?: "😿 Kuch gadbad — dubara try karo"
                    )
                    return@onSuccess
                }
                val d = r.data

                if (!hidden) history.add(Turn("user", text))
                history.add(Turn("assistant", d.reply_speak))

                val bonus = prefs.markPractice()
                val newXp = prefs.xp.first()
                prefs.set(Prefs.XP, newXp)

                _ui.value = _ui.value.copy(
                    busy = false,
                    msgs = _ui.value.msgs + Msg(
                        mine = false,
                        speak = d.reply_speak,
                        roman = d.reply_roman,
                        source = d.reply_source,
                        english = d.reply_english,
                        userInTarget = d.user_in_target,
                        corrected = d.corrected_sentence,
                        tip = d.mini_tip
                    ),
                    xp = newXp,
                    streak = prefs.streak(),
                    status = if (bonus) "🏅 7-DIN CHALLENGE COMPLETE! +50 XP" else ""
                )
            }.onFailure {
                _ui.value = _ui.value.copy(
                    busy = false, mode = CatMode.IDLE,
                    status = "😿 Internet issue — dubara try karo"
                )
            }
        }
    }

    /* ================= QUIZ ================= */

    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz = _quiz.asStateFlow()
    private val _quizLoading = MutableStateFlow(false)
    val quizLoading = _quizLoading.asStateFlow()

    // Agla sawaal background me pehle se taiyar rakhte hain — "Dubara/Next"
    // dabate hi turant dikh jaaye, LLM ka wait na karna pade.
    private var prefetchedQuiz: Quiz? = null
    private var prefetchJob: kotlinx.coroutines.Job? = null

    fun loadQuiz() {
        val s = _ui.value
        if (s.target.isBlank()) return

        prefetchedQuiz?.let { ready ->
            prefetchedQuiz = null
            recentQuestions.add(ready.question)
            _quiz.value = ready
            _quizLoading.value = false
            prefetchNextQuiz()
            return
        }

        if (_quizLoading.value) return
        _quizLoading.value = true
        _quiz.value = null
        viewModelScope.launch {
            runCatching {
                Net.api.quiz(
                    QuizReq(
                        target = s.target, source = s.source, level = s.level,
                        avoid = recentQuestions.takeLast(6)
                    )
                )
            }.onSuccess { r ->
                _quizLoading.value = false
                r.quiz?.let {
                    recentQuestions.add(it.question)
                    _quiz.value = it
                    prefetchNextQuiz()
                }
            }.onFailure {
                _quizLoading.value = false
            }
        }
    }

    private fun prefetchNextQuiz() {
        val s = _ui.value
        if (s.target.isBlank() || prefetchJob?.isActive == true) return
        prefetchJob = viewModelScope.launch {
            runCatching {
                Net.api.quiz(
                    QuizReq(
                        target = s.target, source = s.source, level = s.level,
                        avoid = recentQuestions.takeLast(6)
                    )
                )
            }.onSuccess { r -> r.quiz?.let { prefetchedQuiz = it } }
        }
    }

    /** @return kitna XP mila */
    fun answerQuiz(correct: Boolean): Int {
        val pts = if (correct) 10 else 2
        viewModelScope.launch {
            val newXp = prefs.addXp(pts)
            prefs.markPractice()
            val key = prefs.userKey.first()
            if (key.isNotBlank()) {
                runCatching { Net.api.award(QuizAward(user_key = key, points = pts)) }
            }
            _ui.value = _ui.value.copy(
                xp = newXp,
                quizScore = _ui.value.quizScore + if (correct) 1 else 0,
                streak = prefs.streak()
            )
        }
        return pts
    }

    /* ================= LEADERBOARD ================= */

    private val _lb = MutableStateFlow<LbRes?>(null)
    val lb = _lb.asStateFlow()

    fun loadLeaderboard() {
        viewModelScope.launch {
            val key = prefs.userKey.first()
            runCatching { Net.api.leaderboard(LbReq(user_key = key)) }
                .onSuccess { _lb.value = it }
        }
    }

    /* ================= PRONUNCIATION ================= */

    /** Levenshtein similarity — web wala hi logic */
    fun pronScore(said: String, target: String, roman: String): Int {
        fun norm(x: String) = x.lowercase().replace(Regex("[^\\p{L}\\p{N} ]"), "").trim()
        fun lev(a: String, b: String): Int {
            if (a.isEmpty()) return b.length
            if (b.isEmpty()) return a.length
            val dp = Array(a.length + 1) { IntArray(b.length + 1) }
            for (i in 0..a.length) dp[i][0] = i
            for (j in 0..b.length) dp[0][j] = j
            for (i in 1..a.length) for (j in 1..b.length) {
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1, dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + if (a[i - 1] == b[j - 1]) 0 else 1
                )
            }
            return dp[a.length][b.length]
        }

        val a = norm(said)
        var best = 0.0
        listOf(norm(target), norm(roman)).filter { it.isNotBlank() }.forEach { b ->
            val sim = 1.0 - lev(a, b).toDouble() / maxOf(b.length, 1)
            if (sim > best) best = sim
        }
        return (best.coerceIn(0.0, 1.0) * 100).toInt()
    }

    fun awardPron(pct: Int) {
        val pts = when {
            pct >= 75 -> 15
            pct >= 45 -> 8
            else -> 3
        }
        viewModelScope.launch {
            val newXp = prefs.addXp(pts)
            prefs.markPractice()
            _ui.value = _ui.value.copy(xp = newXp, streak = prefs.streak())
        }
    }

    /* ================= GOOGLE SIGN-IN ================= */

    fun signIn(onDone: (String) -> Unit) {
        viewModelScope.launch {
            val key = prefs.userKey.first()
            GoogleAuth.signIn(getApplication(), key)
                .onSuccess { r ->
                    prefs.set(Prefs.USER_KEY, r.user_key)
                    prefs.set(Prefs.NAME, r.name)
                    prefs.set(Prefs.PICTURE, r.picture)
                    _ui.value = _ui.value.copy(
                        name = r.name, picture = r.picture, signedIn = true
                    )
                    onDone("✅ Namaste ${r.name}! Account jud gaya 🐱")
                }
                .onFailure { onDone("😿 Sign-in nahi hua — dubara try karo") }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            prefs.logout()
            _ui.value = _ui.value.copy(name = "", picture = "", signedIn = false)
        }
    }

    /* ================= SETTINGS ================= */

    fun setNotif(on: Boolean, time: String) {
        viewModelScope.launch {
            prefs.set(Prefs.NOTIF_ON, on)
            prefs.set(Prefs.NOTIF_TIME, time)
            if (on) Notifs.schedule(getApplication(), time)
            else Notifs.cancelAll(getApplication())
        }
    }

    fun setDark(on: Boolean) {
        viewModelScope.launch { prefs.set(Prefs.DARK, on) }
    }

    fun setVoice(name: String) {
        viewModelScope.launch { prefs.set(Prefs.VOICE, name) }
    }

    fun savePhrase(m: Msg) {
        viewModelScope.launch {
            prefs.savePhrase("${m.speak}|${m.roman}|${m.source}")
        }
    }

    /** Telegram bot ke liye 6-digit code */
    fun linkCode(onCode: (String?) -> Unit) {
        viewModelScope.launch {
            val key = prefs.userKey.first()
            if (key.isBlank()) { onCode(null); return@launch }
            runCatching { Net.api.linkCode(LinkReq(user_key = key)) }
                .onSuccess { onCode(if (it.ok) it.code else null) }
                .onFailure { onCode(null) }
        }
    }
}
