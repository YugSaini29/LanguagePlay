package `in`.languageplay.meowguru.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.webkit.JavascriptInterface
import android.webkit.WebView
import java.util.Locale

/**
 * Road/Grammar/Sentence/Metro games browser ka Web Speech API
 * (`speechSynthesis.speak()`) use karte hain taaki har word/sentence bola
 * jaaye. Android ke embedded WebView (Chromium) me `speechSynthesis` ek
 * jaana-maana limitation ki wajah se silently kuch nahi karta — standalone
 * Chrome app me chalta hai, in-app WebView me nahi.
 *
 * Fix: [TTS_HOOK_JS] page load hote hi `window.speechSynthesis.speak` ko
 * overwrite kar deta hai taaki har call yahan [speak] tak pahunche, jo
 * Android ke native TextToSpeech engine se bulwata hai (wahi engine jo Chat
 * tab me already kaam kar raha hai).
 */
class GameTtsBridge(context: Context, private val webView: WebView) {

    @Volatile private var ready = false

    private val tts: TextToSpeech = TextToSpeech(context.applicationContext) { status ->
        ready = status == TextToSpeech.SUCCESS
    }.also { engine ->
        engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) = notifyEnd(utteranceId)
            @Deprecated("Deprecated in Java, still required to override")
            override fun onError(utteranceId: String?) = notifyEnd(utteranceId)
            override fun onError(utteranceId: String?, errorCode: Int) = notifyEnd(utteranceId)
        })
    }

    private fun notifyEnd(utteranceId: String?) {
        val id = utteranceId?.toIntOrNull() ?: return
        webView.post {
            webView.evaluateJavascript("window.__meowNativeTtsEnd && window.__meowNativeTtsEnd($id);", null)
        }
    }

    /** Called from JS: AndroidTTS.speak(text, lang, id) */
    @JavascriptInterface
    fun speak(text: String, lang: String, id: Int) {
        if (!ready || text.isBlank()) {
            notifyEnd(id.toString())
            return
        }
        val locale = runCatching {
            if (lang.isNotBlank()) Locale.forLanguageTag(lang) else Locale.getDefault()
        }.getOrDefault(Locale.getDefault())

        val res = tts.setLanguage(locale)
        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Us bhasha ka voice pack device pe nahi hai — English fallback,
            // taaki kam se kam kuch awaaz toh aaye, chup na rahe.
            tts.language = Locale.US
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, id.toString())
    }

    /** Called from JS: AndroidTTS.stop() */
    @JavascriptInterface
    fun stop() {
        runCatching { tts.stop() }
    }

    fun release() {
        runCatching { tts.stop() }
        runCatching { tts.shutdown() }
    }

    companion object {
        /**
         * Page ke apne script chalne se PEHLE inject hona chahiye
         * (WebViewCompat.addDocumentStartJavaScript se), taaki jab tak game
         * ka JS `speechSynthesis.speak(...)` bulaaye, ye hook already lag
         * chuka ho.
         */
        const val TTS_HOOK_JS = """
            (function() {
              if (window.__meowTtsHooked) return;
              window.__meowTtsHooked = true;
              var pending = {};
              var nextId = 1;
              window.__meowNativeTtsEnd = function(id) {
                var u = pending[id];
                delete pending[id];
                if (u && typeof u.onend === 'function') { try { u.onend(); } catch (e) {} }
              };
              function hook() {
                if (!window.speechSynthesis) return;
                window.speechSynthesis.speak = function(utterance) {
                  var id = nextId++;
                  pending[id] = utterance;
                  if (utterance && typeof utterance.onstart === 'function') {
                    try { utterance.onstart(); } catch (e) {}
                  }
                  if (window.AndroidTTS) {
                    AndroidTTS.speak(
                      (utterance && utterance.text) || '',
                      (utterance && utterance.lang) || document.documentElement.lang || '',
                      id
                    );
                  }
                };
                window.speechSynthesis.cancel = function() { if (window.AndroidTTS) AndroidTTS.stop(); };
                window.speechSynthesis.pause = function() {};
                window.speechSynthesis.resume = function() {};
                window.speechSynthesis.getVoices = function() { return []; };
              }
              hook();
            })();
        """
    }
}
