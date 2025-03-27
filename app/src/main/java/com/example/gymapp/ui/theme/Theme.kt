package com.example.gymapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50), // Verde principal
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2E7D32), // Verde más oscuro
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF81C784), // Verde secundario
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF388E3C), // Verde más oscuro para contenedores
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFFA5D6A7), // Verde terciario
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF1B5E20), // Verde muy oscuro
    onTertiaryContainer = Color.White,
    background = Color(0xFF121212), // Negro de fondo
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E), // Negro más claro para superficies
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D), // Negro más claro para variantes de superficie
    onSurfaceVariant = Color.White,
    outline = Color(0xFF4CAF50), // Verde para bordes
    outlineVariant = Color(0xFF2E7D32), // Verde más oscuro para variantes de bordes
    scrim = Color.Black,
    error = Color(0xFFCF6679),
    errorContainer = Color(0xFFB00020),
    onError = Color.White,
    onErrorContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50), // Verde principal
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA5D6A7), // Verde claro para contenedores
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF81C784), // Verde secundario
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFC8E6C9), // Verde muy claro para contenedores secundarios
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFF388E3C), // Verde terciario
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFA5D6A7), // Verde claro para contenedores terciarios
    onTertiaryContainer = Color.Black,
    background = Color(0xFFF5F5F5), // Gris muy claro para fondo
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE8F5E9), // Verde muy claro para variantes de superficie
    onSurfaceVariant = Color.Black,
    outline = Color(0xFF4CAF50), // Verde para bordes
    outlineVariant = Color(0xFF81C784), // Verde claro para variantes de bordes
    scrim = Color.Black,
    error = Color(0xFFB00020),
    errorContainer = Color(0xFFCF6679),
    onError = Color.White,
    onErrorContainer = Color.White
)

@Composable
fun GymAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}