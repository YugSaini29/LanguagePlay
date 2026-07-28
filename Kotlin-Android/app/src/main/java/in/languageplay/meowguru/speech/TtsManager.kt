package `in`.languageplay.meowguru.speech

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import java.util.Locale

/**
 * Native TTS — Meow Guru ladka hai, isliye MALE awaaz prefer hoti hai.
 * User chahe toh Settings se apni voice chun sakta hai.
 */
class TtsManager(ctx: Context) {

    private var tts: TextToSpeech? = null
    private var ready = false
    var onStart: (() -> Unit)? = null
    var onDone: (() -> Unit)? = null

    /** user ki chuni hui voice ka naam (khali = auto male) */
    var preferredVoice: String = ""

    private val femaleHints = listOf(
        "female", "woman", "girl", "aditi", "kalpana", "swara", "neerja", "shruti",
        "samantha", "zira", "karen", "moira", "tessa", "fiona", "serena", "joanna",
        "#female", "-f-", "_f_"
    )
    private val maleHints = listOf(
        "male", "man", "boy", "david", "mark", "alex", "daniel", "fred", "george",
        "rishi", "hemant", "ravi", "arjun", "matthew", "brian", "russell",
        "#male", "-m-", "_m_"
    )

    init {
        tts = TextToSpeech(ctx) { status ->
            ready = status == TextToSpeech.SUCCESS
        }
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) { onStart?.invoke() }
            override fun onDone(utteranceId: String?) { onDone?.invoke() }
            @Deprecated("deprecated")
            override fun onError(utteranceId: String?) { onDone?.invoke() }
            override fun onError(utteranceId: String?, errorCode: Int) { onDone?.invoke() }
        })
    }

    /** + = male (jo humein chahiye) */
    private fun score(v: Voice): Int {
        val n = v.name.lowercase()
        val fem = femaleHints.any { n.contains(it) }
        val male = maleHints.any { n.contains(it) }
        return if (male) 10 else if (fem) -10 else 0
    }

    fun voicesFor(localeTag: String): List<Voice> {
        val t = tts ?: return emptyList()
        val base = localeTag.substringBefore('-')
        return runCatching {
            t.voices.orEmpty()
                .filter { v ->
                    val l = v.locale.toLanguageTag()
                    l.equals(localeTag, true) || l.startsWith(base, true)
                }
                .sortedByDescending { score(it) }
        }.getOrDefault(emptyList())
    }

    private fun pickVoice(localeTag: String): Voice? {
        val list = voicesFor(localeTag)
        if (list.isEmpty()) return null
        preferredVoice.takeIf { it.isNotBlank() }?.let { saved ->
            list.firstOrNull { it.name == saved }?.let { return it }
        }
        return list.first()   // sabse male-scoring
    }

    fun isMale(v: Voice?): Boolean = v != null && score(v) > 0

    /**
     * @param slow true = word-by-word dheema (learners ke liye)
     */
    fun speak(text: String, localeTag: String, slow: Boolean = false) {
        if (!ready || text.isBlank()) { onDone?.invoke(); return }
        val t = tts ?: return

        val loc = Locale.forLanguageTag(localeTag)
        val res = t.setLanguage(loc)
        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
            t.language = Locale.ENGLISH
        }

        val v = pickVoice(localeTag)
        if (v != null) t.voice = v

        // male voice mile toh normal pitch; sirf female mile toh neeche — mardana lage
        t.setPitch(if (isMale(v)) 1.0f else 0.75f)
        t.setSpeechRate(if (slow) 0.45f else 0.9f)

        val speakText = if (slow) text.split(Regex("\\s+")).joinToString(", ") else text
        val params = Bundle()
        t.speak(speakText, TextToSpeech.QUEUE_FLUSH, params, "meow-${System.currentTimeMillis()}")
    }

    fun stop() {
        runCatching { tts?.stop() }
    }

    fun release() {
        runCatching { tts?.stop(); tts?.shutdown() }
        tts = null
        ready = false
    }
}
