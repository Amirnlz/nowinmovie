package com.amirnlz.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    onSeriesClicked: (Long) -> Unit,
) {
    Text("This is Home Screen")
}