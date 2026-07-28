package `in`.languageplay.meowguru.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Brand palette — web app se same
val Navy = Color(0xFF1E3A6E)
val OrangeM = Color(0xFFF5871F)
val Sky = Color(0xFF4FB3F6)
val GreenM = Color(0xFF3FBF6F)
val RedM = Color(0xFFE85D5D)
val Ink = Color(0xFF243447)
val Muted = Color(0xFF8B98A8)
val CardLight = Color(0xFFFFFFFF)
val BgLight = Color(0xFFF2F7FD)
val CardDark = Color(0xFF152238)
val BgDark = Color(0xFF0F1A2E)
val BubbleDark = Color(0xFF1E3050)

private val LightScheme = lightColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = OrangeM,
    onSecondary = Color.White,
    tertiary = Sky,
    background = BgLight,
    onBackground = Ink,
    surface = CardLight,
    onSurface = Ink,
    error = RedM
)

private val DarkScheme = darkColorScheme(
    primary = Sky,
    onPrimary = Color.White,
    secondary = OrangeM,
    onSecondary = Color.White,
    tertiary = Sky,
    background = BgDark,
    onBackground = Color(0xFFD8E6F7),
    surface = CardDark,
    onSurface = Color(0xFFD8E6F7),
    error = RedM
)

@Composable
fun MeowTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (dark) DarkScheme else LightScheme,
        content = content
    )
}
