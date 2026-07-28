package `in`.languageplay.meowguru.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import `in`.languageplay.meowguru.R
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class CatMode { IDLE, LISTEN, THINK, SPEAK }

/**
 * Web app wali cat — round circle, hamesha blue radiating waves,
 * bolte waqt lip-sync (4 frames), random blink, sochte waqt sleepy.
 */
@Composable
fun CatView(mode: CatMode, modifier: Modifier = Modifier) {

    // --- lip-sync frames ---
    var mouth by remember { mutableIntStateOf(0) }
    var blink by remember { mutableStateOf(false) }

    LaunchedEffect(mode) {
        if (mode == CatMode.SPEAK) {
            while (true) {
                mouth = Random.nextInt(0, 4)          // closed/half/open/round
                delay(Random.nextLong(110, 190))
            }
        } else {
            mouth = 0
        }
    }

    // random blink (sirf jab bol/soch nahi raha)
    LaunchedEffect(mode) {
        while (true) {
            delay(Random.nextLong(2600, 5200))
            if (mode == CatMode.IDLE || mode == CatMode.LISTEN) {
                blink = true
                delay(150)
                blink = false
            }
        }
    }

    val sprite = when {
        mode == CatMode.THINK -> R.drawable.cat_sleepy
        blink -> R.drawable.cat_blink
        mode == CatMode.SPEAK -> when (mouth) {
            1 -> R.drawable.cat_half
            2 -> R.drawable.cat_open
            3 -> R.drawable.cat_round
            else -> R.drawable.cat_closed
        }
        else -> R.drawable.cat_closed
    }

    // --- animations ---
    val inf = rememberInfiniteTransition(label = "cat")

    val breathe by inf.animateFloat(
        initialValue = 1f, targetValue = 1.02f,
        animationSpec = infiniteRepeatable(tween(1700, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "breathe"
    )

    // waves speed mode ke hisaab se
    val waveMs = when (mode) {
        CatMode.SPEAK -> 1300
        CatMode.LISTEN -> 1700
        else -> 2400
    }

    Box(modifier = modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {

        // 3 radiating blue waves
        repeat(3) { i ->
            val p by inf.animateFloat(
                initialValue = 0f, targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    tween(waveMs, delayMillis = i * (waveMs / 3), easing = LinearEasing)
                ),
                label = "wave$i"
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .scale(1f + p * 0.5f)
                    .clip(CircleShape)
                    .background(Sky.copy(alpha = (1f - p) * 0.28f))
            )
        }

        // cat circle
        Box(
            Modifier
                .fillMaxSize(0.86f)
                .scale(breathe)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(sprite),
                contentDescription = "Meow Guru",
                contentScale = ContentScale.Fit,     // poori cat — crop nahi
                modifier = Modifier.fillMaxSize(0.95f)
            )
        }
    }
}
