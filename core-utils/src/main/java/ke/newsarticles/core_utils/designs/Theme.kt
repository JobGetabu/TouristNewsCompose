package ke.newsarticles.core_utils.designs

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Primary,
    secondary = Ascent,
    background = Color.Black,
    surface = Color.White,
    onSurface = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = DarkPrimary,
    primaryVariant = Color(0xFFedf6f9),
    secondary = Ascent,
    background = Color.White,
    surface = Color.Black,
    onSurface = DarkPrimary
)

@Composable
fun NewsArticlesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}
