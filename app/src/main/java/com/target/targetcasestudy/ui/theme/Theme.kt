package com.target.targetcasestudy.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    secondary = DarkRed,
    tertiary = Green
)

private val LightColorScheme = lightColorScheme(
    primary = Red,
    secondary = DarkRed,
    tertiary = Green,
//    background = Red,
//    surface = Red,
//    onPrimary = Red,
//    onSecondary = Red,
//    onTertiary = Red,
//    onBackground = Red,
//    onSurface = Red,
)

@Composable
fun AndroidcasestudymainTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    systemUiController.setStatusBarColor(color = Red)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}