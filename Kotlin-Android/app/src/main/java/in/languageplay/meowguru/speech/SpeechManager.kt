package `in`.languageplay.meowguru.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

/**
 * Native STT — web wali saari samasyaayein (hang, onend na aana) yahan nahi hain.
 * Android khud silence detect karke result de deta hai.
 */
class SpeechManager(private val ctx: Context) {

    private var sr: SpeechRecognizer? = null
    var listening = false
        private set

    fun available(): Boolean = SpeechRecognizer.isRecognitionAvailable(ctx)

    fun start(
        locale: String,
        onPartial: (String) -> Unit,
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (listening) return
        if (!available()) {
            onError("Is phone me speech recognition nahi hai")
            return
        }

        stop()   // purana instance saaf

        sr = SpeechRecognizer.createSpeechRecognizer(ctx).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    listening = true
                }

                override fun onPartialResults(partial: Bundle?) {
                    partial?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull()?.let { if (it.isNotBlank()) onPartial(it) }
                }

                override fun onResults(results: Bundle?) {
                    listening = false
                    val text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        ?.firstOrNull().orEmpty()
                    destroySr()
                    if (text.isBlank()) onError("Kuch sunai nahi diya — dubara bolo")
                    else onResult(text)
                }

                override fun onError(error: Int) {
                    listening = false
                    destroySr()
                    val msg = when (error) {
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                            "Mic permission do — Settings me allow karo"
                        SpeechRecognizer.ERROR_NO_MATCH,
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                            "Kuch sunai nahi diya — dubara bolo"
                        SpeechRecognizer.ERROR_NETWORK,
                        SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                            "Internet slow hai — dubara try karo"
                        SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED,
                        SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE ->
                            "Is bhasha me mic support nahi — type karke bhejo"
                        else -> "Mic error — dubara try karo"
                    }
                    onError(msg)
                }

                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(v: Float) {}
                override fun onBufferReceived(b: ByteArray?) {}
                override fun onEndOfSpeech() { listening = false }
                override fun onEvent(t: Int, p: Bundle?) {}
            })
        }

        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            // chup hone ke baad kitna wait — auto-send jaisa behaviour
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1500L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1500L)
        }

        try {
            sr?.startListening(i)
            listening = true
        } catch (e: Exception) {
            Log.e("Meow", "stt start fail", e)
            listening = false
            onError("Mic shuru nahi hua")
        }
    }

    /** User ne khud stop dabaya — jo abhi tak suna wo result me aa jayega */
    fun stop() {
        try { sr?.stopListening() } catch (_: Exception) {}
        listening = false
    }

    private fun destroySr() {
        try { sr?.destroy() } catch (_: Exception) {}
        sr = null
    }

    fun release() {
        destroySr()
        listening = false
    }
}
