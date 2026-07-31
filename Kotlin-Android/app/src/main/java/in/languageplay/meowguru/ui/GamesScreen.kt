package `in`.languageplay.meowguru.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import `in`.languageplay.meowguru.data.GameCategory
import `in`.languageplay.meowguru.data.GameItem
import `in`.languageplay.meowguru.data.Games
import `in`.languageplay.meowguru.speech.GameTtsBridge

/**
 * Games tab ka home — Road Series, Grammar Book, Kids Trail (categories with
 * horizontal scroll) + Sentence Road / Language Cards / Daily Verbs / Metro
 * (standalone highlight cards). Tap karne pe [GameWebViewScreen] khulta hai.
 */
@Composable
fun GamesHubScreen(onPlay: (GameItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Column {
                Text("🎮 Meow Guru Games", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(
                    "Saare LanguagePlay games ek jagah — sound on rakho, koi crash-game-over nahi 🙂",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("⭐ Featured", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Games.singles.forEach { game -> FeaturedGameCard(game, onClick = { onPlay(game) }) }
            }
        }

        items(Games.categories) { category ->
            GameCategoryRow(category, onPlay = onPlay)
        }
    }
}

@Composable
private fun FeaturedGameCard(game: GameItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(game.emoji, fontSize = 24.sp)
            }
            Column(Modifier.weight(1f)) {
                Text(game.title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyLarge)
                Text(
                    game.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
            FilledTonalButton(onClick = onClick) { Text("Khelo ▶") }
        }
    }
}

@Composable
private fun GameCategoryRow(category: GameCategory, onPlay: (GameItem) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(category.emoji, fontSize = 20.sp)
            Text(category.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        Text(
            category.tagline,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(category.games) { game ->
                GameTile(game, onClick = { onPlay(game) })
            }
        }
    }
}

@Composable
private fun GameTile(game: GameItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).height(110.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(game.emoji, fontSize = 26.sp)
            Column {
                Text(
                    game.title,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
                Text(
                    game.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}

/**
 * Full-screen native game player. Existing WebGL game ko in-app WebView me
 * chalata hai — top bar (back + title + reload), loading spinner, aur
 * offline/error state ke saath. JS + DOM storage on (games ko chahiye),
 * hardware acceleration WebView ke liye Android manifest me already on hai.
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameWebViewScreen(game: GameItem, onBack: () -> Unit) {
    var progress by remember(game.url) { mutableIntStateOf(0) }
    var loading by remember(game.url) { mutableStateOf(true) }
    var failed by remember(game.url) { mutableStateOf(false) }
    var reloadKey by remember(game.url) { mutableIntStateOf(0) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var ttsBridge by remember(reloadKey) { mutableStateOf<GameTtsBridge?>(null) }
    DisposableEffect(ttsBridge) {
        onDispose { ttsBridge?.release() }
    }

    // Kuch OEM ROMs par WebView audio tab tak chup rehta hai jab tak app
    // explicitly music/media audio focus na maange — game khulte hi maango,
    // band hote hi chhod do.
    val ctx = androidx.compose.ui.platform.LocalContext.current
    DisposableEffect(Unit) {
        val am = ctx.getSystemService(android.content.Context.AUDIO_SERVICE) as android.media.AudioManager
        val req = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.media.AudioFocusRequest.Builder(android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_GAME)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                ).build().also { am.requestAudioFocus(it) }
        } else null
        @Suppress("DEPRECATION")
        if (req == null) am.requestAudioFocus({ }, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        onDispose {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && req != null) am.abandonAudioFocusRequest(req)
        }
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("${game.emoji} ${game.title}", maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Wapas")
                }
            },
            actions = {
                IconButton(onClick = {
                    failed = false
                    loading = true
                    reloadKey++
                    webViewRef?.reload()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reload")
                }
            }
        )

        Box(Modifier.fillMaxSize()) {
            if (!failed) {
                key(reloadKey) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { ctx ->
                            WebView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                settings.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    mediaPlaybackRequiresUserGesture = false
                                    cacheMode = WebSettings.LOAD_DEFAULT
                                    loadWithOverviewMode = true
                                    useWideViewPort = true
                                    // Road/Grammar games WebGL canvas use karte hain — hardware
                                    // layer WebView ke liye already default accelerated hai.
                                }

                                // Sirf apni trusted domains (languageplay.in / examica.in) yahan
                                // load hoti hain, isliye JS interface expose karna safe hai.
                                val bridge = GameTtsBridge(ctx, this)
                                ttsBridge = bridge
                                addJavascriptInterface(bridge, "AndroidTTS")

                                val docStartSupported = WebViewFeature.isFeatureSupported(WebViewFeature.DOCUMENT_START_SCRIPT)
                                if (docStartSupported) {
                                    WebViewCompat.addDocumentStartJavaScript(
                                        this, GameTtsBridge.TTS_HOOK_JS, setOf("*")
                                    )
                                }

                                webChromeClient = object : WebChromeClient() {
                                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                        progress = newProgress
                                        if (newProgress >= 100) loading = false
                                    }
                                }
                                webViewClient = object : WebViewClient() {
                                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                        loading = true
                                        // Purane WebView (jahan document-start script support nahi
                                        // hai) ke liye fallback — thoda late lag sakta hai par
                                        // kuch na hone se behtar hai.
                                        if (!docStartSupported) {
                                            view?.evaluateJavascript(GameTtsBridge.TTS_HOOK_JS, null)
                                        }
                                    }
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        loading = false
                                        if (!docStartSupported) {
                                            view?.evaluateJavascript(GameTtsBridge.TTS_HOOK_JS, null)
                                        }
                                    }
                                    override fun onReceivedError(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                        error: WebResourceError?
                                    ) {
                                        if (request?.isForMainFrame != false) {
                                            failed = true
                                            loading = false
                                        }
                                    }
                                }
                                loadUrl(game.url)
                                webViewRef = this
                            }
                        },
                        update = { /* URL sirf reloadKey badalne pe reload hota hai */ }
                    )
                }
                if (loading) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(progress = { progress / 100f })
                        Spacer(Modifier.height(10.dp))
                        Text("${game.emoji} Load ho raha hai… $progress%")
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.SignalWifiOff, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.height(12.dp))
                    Text("😿 Game load nahi hua — internet check karo", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = {
                        failed = false
                        loading = true
                        reloadKey++
                    }) { Text("Phir se try karo") }
                }
            }
        }
    }
}
