package com.wardatulkeskin.comp304lab2.ext1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Dark Colour Theme with customization
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),         // Teal-ish Primary
    onPrimary = Color.White,
    secondary = Color(0xFFB39DDB),
    onSecondary = Color.White,
    tertiary = Color(0xFFFFAB91),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFFFFFFF)
)

// Light Colour Theme with customization
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00897B),         // Teal Primary
    onPrimary = Color.White,
    secondary = Color(0xFF7E57C2),
    onSecondary = Color.White,
    tertiary = Color(0xFFFF7043),
    background = Color(0xFFFDFDFD),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F)
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */

@Composable
fun Comp304lab2ext1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Typography aligns with Material 3 default token system
    val typography = Typography(
        bodyLarge = Typography().bodyLarge.copy(color = colorScheme.onSurface),
        titleLarge = Typography().titleLarge.copy(color = colorScheme.primary),
        labelLarge = Typography().labelLarge.copy(color = colorScheme.onPrimary)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}