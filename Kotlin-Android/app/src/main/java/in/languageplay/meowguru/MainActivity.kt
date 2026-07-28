package `in`.languageplay.meowguru

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.languageplay.meowguru.data.GameItem
import `in`.languageplay.meowguru.data.Langs
import `in`.languageplay.meowguru.data.Prefs
import `in`.languageplay.meowguru.notif.Notifs
import `in`.languageplay.meowguru.speech.SpeechManager
import `in`.languageplay.meowguru.speech.TtsManager
import `in`.languageplay.meowguru.ui.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var stt: SpeechManager
    private lateinit var tts: TtsManager

    private val micPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (!ok) toast("🎤 Mic permission chahiye — Settings me allow karo")
    }
    private val notifPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Games (WebView) aur TTS dono STREAM_MUSIC pe bajte hain — hardware
        // volume buttons ko usi stream se jodo, warna ringtone volume control
        // hota hai aur media chup dikhta hai (games ki awaaz nahi aati).
        volumeControlStream = android.media.AudioManager.STREAM_MUSIC

        stt = SpeechManager(this)
        tts = TtsManager(this)
        Notifs.createChannels(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notifPerm.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            val vm: MeowVm = viewModel()
            val prefs = remember { Prefs(this) }
            val scope = rememberCoroutineScope()

            val s by vm.ui.collectAsState()
            val dark by prefs.dark.collectAsState(initial = false)
            val notifOn by prefs.notifOn.collectAsState(initial = true)
            val notifTime by prefs.notifTime.collectAsState(initial = "19:00")
            val savedVoice by prefs.voice.collectAsState(initial = "")
            val days by prefs.days.collectAsState(initial = emptySet())

            var listening by remember { mutableStateOf(false) }
            var tab by remember { mutableIntStateOf(0) }
            var pickingLangs by remember { mutableStateOf(false) }
            var pronTarget by remember { mutableStateOf<Pair<String, String>?>(null) }
            var activeGame by remember { mutableStateOf<GameItem?>(null) }

            // Game khula ho toh hardware back usse band kare, poori app se bahar na nikale
            if (activeGame != null) {
                BackHandler { activeGame = null }
            }

            // TTS ki chuni hui voice
            LaunchedEffect(savedVoice) { tts.preferredVoice = savedVoice }

            // cat ka mode TTS ke saath sync
            LaunchedEffect(Unit) {
                tts.onStart = { vm.setMode(CatMode.SPEAK, "🐱 Meow Guru bol raha hai…") }
                tts.onDone = { vm.setMode(CatMode.IDLE, "") }
                // notifications schedule
                if (prefs.notifOn.first()) Notifs.schedule(this@MainActivity, prefs.notifTime.first())
            }

            // pehli baar → language picker
            val needLangs = s.source.isBlank() || s.target.isBlank() || pickingLangs

            MeowTheme(dark = dark) {
                if (needLangs) {
                    LanguageScreen { src, tgt ->
                        pickingLangs = false
                        vm.chooseLangs(src, tgt)
                    }
                    return@MeowTheme
                }

                Scaffold(
                    bottomBar = {
                        if (activeGame == null) NavigationBar {
                            NavigationBarItem(
                                selected = tab == 0, onClick = { tab = 0 },
                                icon = { Icon(Icons.Default.Chat, null) },
                                label = { Text("Seekho") }
                            )
                            NavigationBarItem(
                                selected = tab == 1, onClick = { tab = 1 },
                                icon = { Icon(Icons.Default.SportsEsports, null) },
                                label = { Text("Quiz") }
                            )
                            NavigationBarItem(
                                selected = tab == 2, onClick = { tab = 2 },
                                icon = { Icon(Icons.Default.EmojiEvents, null) },
                                label = { Text("Rank") }
                            )
                            NavigationBarItem(
                                selected = tab == 3, onClick = { tab = 3 },
                                icon = { Icon(Icons.Default.VideogameAsset, null) },
                                label = { Text("Games") }
                            )
                            NavigationBarItem(
                                selected = tab == 4, onClick = { tab = 4 },
                                icon = { Icon(Icons.Default.Settings, null) },
                                label = { Text("Settings") }
                            )
                        }
                    }
                ) { pad ->
                    Box(Modifier.padding(if (activeGame == null) pad else PaddingValues(0.dp))) {
                        if (activeGame != null) {
                            GameWebViewScreen(game = activeGame!!, onBack = { activeGame = null })
                            return@Box
                        }
                        when (tab) {
                            0 -> ChatScreen(
                                vm = vm,
                                listening = listening,
                                onMic = {
                                    if (!hasMic()) {
                                        micPerm.launch(Manifest.permission.RECORD_AUDIO)
                                    } else {
                                        tts.stop()
                                        listening = true
                                        vm.setMode(CatMode.LISTEN, "👂 Bolo…")
                                        val loc = Langs.ttsOf(s.source)
                                        stt.start(
                                            locale = loc,
                                            onPartial = { vm.setStatus("👂 $it") },
                                            onResult = { text ->
                                                listening = false
                                                pronTarget?.let { (t, r) ->
                                                    // pronunciation test mode
                                                    pronTarget = null
                                                    val pct = vm.pronScore(text, t, r)
                                                    vm.awardPron(pct)
                                                    vm.setMode(CatMode.IDLE, "🗣 Pronunciation: $pct% " +
                                                            if (pct >= 75) "⭐⭐⭐ Wah!" else if (pct >= 45) "⭐⭐ Achha!" else "⭐ Phir se!")
                                                } ?: vm.send(text)
                                            },
                                            onError = { msg ->
                                                listening = false
                                                pronTarget = null
                                                vm.setMode(CatMode.IDLE, "😿 $msg")
                                            }
                                        )
                                    }
                                },
                                onStopMic = { stt.stop(); listening = false },
                                onSpeak = { text, roman, slow ->
                                    val loc = Langs.ttsOf(s.target)
                                    tts.speak(text.ifBlank { roman }, loc, slow)
                                },
                                onTryPron = { target, roman ->
                                    if (!hasMic()) micPerm.launch(Manifest.permission.RECORD_AUDIO)
                                    else {
                                        pronTarget = target to roman
                                        tts.stop()
                                        listening = true
                                        vm.setMode(CatMode.LISTEN, "🗣 Bolo: $target")
                                        stt.start(
                                            locale = Langs.ttsOf(s.target),
                                            onPartial = { vm.setStatus("👂 $it") },
                                            onResult = { said ->
                                                listening = false
                                                pronTarget = null
                                                val pct = vm.pronScore(said, target, roman)
                                                vm.awardPron(pct)
                                                vm.setMode(
                                                    CatMode.IDLE,
                                                    "🗣 $pct% " + when {
                                                        pct >= 75 -> "⭐⭐⭐ Perfect! +15 XP"
                                                        pct >= 45 -> "⭐⭐ Achha try! +8 XP"
                                                        else -> "⭐ 🐢 Slow sunke phir bolo (+3 XP)"
                                                    }
                                                )
                                            },
                                            onError = { msg ->
                                                listening = false; pronTarget = null
                                                vm.setMode(CatMode.IDLE, "😿 $msg")
                                            }
                                        )
                                    }
                                }
                            )

                            1 -> QuizScreen(vm) { t, r, slow ->
                                tts.speak(t.ifBlank { r }, Langs.ttsOf(s.target), slow)
                            }

                            2 -> LeaderboardScreen(vm)

                            3 -> GamesHubScreen(onPlay = { game -> activeGame = game })

                            4 -> SettingsScreen(
                                vm = vm,
                                days = days,
                                voices = tts.voicesFor(Langs.ttsOf(s.target)).map { it.name },
                                currentVoice = savedVoice,
                                onVoicePick = { v -> vm.setVoice(v); tts.preferredVoice = v },
                                onTestVoice = { v ->
                                    tts.preferredVoice = v
                                    tts.speak("Meow! Namaste, main Meow Guru hoon.", Langs.ttsOf(s.target))
                                },
                                onChangeLangs = { pickingLangs = true },
                                onOpenBot = {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BOT_URL)))
                                },
                                dark = dark,
                                onDark = { vm.setDark(it) },
                                notifOn = notifOn,
                                notifTime = notifTime,
                                onNotif = { on, t -> vm.setNotif(on, t) },
                                onSignIn = { vm.signIn { msg -> toast(msg) } }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun hasMic() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.RECORD_AUDIO
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    private fun toast(m: String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()

    override fun onDestroy() {
        super.onDestroy()
        stt.release()
        tts.release()
    }
}
