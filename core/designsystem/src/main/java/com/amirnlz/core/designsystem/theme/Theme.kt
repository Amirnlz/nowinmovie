package com.amirnlz.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = Red80,
    onPrimary = Neutral10,
    primaryContainer = primaryContainerLightColor,
    onPrimaryContainer = onPrimaryContainerLightColor,
    inversePrimary = Red40,
    secondary = Gold80,
    onSecondary = Neutral90,
    secondaryContainer = secondaryContainerLightColor,
    onSecondaryContainer = onSecondaryContainerLightColor,
    tertiary = Teal80,
    onTertiary = Neutral90,
    tertiaryContainer = tertiaryContainerLightColor,
    onTertiaryContainer = onTertiaryContainerLightColor,
    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral90,
    surfaceVariant = surfaceVariantLightColor,
    onSurfaceVariant = onSecondaryContainerLightColor,
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral10,
    outline = outlineLightColor,
    outlineVariant = outlineVariantLightColor,
    error = Error80,
    onError = Neutral10,
    errorContainer = errorContainerLightColor,
    onErrorContainer = onErrorContainerLightColor,
    scrim = scrimLightColor
)

private val DarkColorScheme = darkColorScheme(
    primary = Red40,
    onPrimary = Neutral10Dark,
    primaryContainer = primaryContainerDarkColor,
    onPrimaryContainer = onPrimaryContainerDarkColor,
    inversePrimary = Red80,
    secondary = Gold40,
    onSecondary = Neutral10Dark,
    secondaryContainer = secondaryContainerDarkColor,
    onSecondaryContainer = onSecondaryContainerDarkColor,
    tertiary = Teal40,
    onTertiary = Neutral10Dark,
    tertiaryContainer = tertiaryContainerDarkColor,
    onTertiaryContainer = onTertiaryContainerDarkColor,
    background = Neutral10Dark,
    onBackground = Neutral90Dark,
    surface = Neutral10Dark,
    onSurface = Neutral90Dark,
    surfaceVariant = surfaceVariantDarkColor,
    onSurfaceVariant = onSurfaceVariantDarkColor,
    inverseSurface = Neutral90Dark,
    inverseOnSurface = Neutral10Dark,
    outline = outlineDarkColor,
    outlineVariant = onSurfaceVariantDarkColor,
    error = Error40,
    onError = Neutral10Dark,
    errorContainer = errorContainerDarkColor,
    onErrorContainer = onErrorContainerDarkColor,
    scrim = scrimDarkColor
)


@Composable
fun NowinmovieTheme(
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

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalDimensions provides Dimens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object NowinmovieTheme {
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val dimens: Dimens
        @Composable
        get() = LocalDimensions.current
}