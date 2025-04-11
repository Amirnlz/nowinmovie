package com.amirnlz.core.designsystem.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimens {
  val paddingExtraSmall: Dp = 4.dp
  val paddingSmall: Dp = 8.dp
  val paddingExtraMedium: Dp = 12.dp
  val paddingMedium: Dp = 16.dp
  val paddingLarge: Dp = 24.dp
  val paddingExtraLarge: Dp = 32.dp

  val cornerRadiusSmall: Dp = 4.dp
  val cornerRadiusMedium: Dp = 8.dp
  val cornerRadiusLarge: Dp = 16.dp

  val iconSizeSmall: Dp = 16.dp
  val iconSizeMedium: Dp = 24.dp
  val iconSizeLarge: Dp = 32.dp
  val iconSizeExtraLarge: Dp = 54.dp

  val posterAspectRatio = 2f / 3f
  val backdropAspectRatio = 16f / 9f

  val screenPadding: PaddingValues =
    PaddingValues(horizontal = paddingExtraMedium, vertical = paddingSmall)
}

val LocalDimensions = staticCompositionLocalOf { Dimens }
