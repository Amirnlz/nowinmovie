package com.amirnlz.feature.home

import androidx.compose.animation.animateColorAsState

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {


    HomeScreen(
        modifier = modifier,
        onMovieClicked = onMovieClicked,

        )

}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
    ) {
        HomeMovieTypeTabs()

    }
}

@Composable
fun HomeMovieTypeTabs(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    val list = HomeContract.MovieType.entries.map { it.name }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Start
    ) {
        list.forEachIndexed { index, movieType ->
            val isSelected = index == selectedIndex
            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.outline,
                animationSpec = tween(durationMillis = 300)
            )
            Text(
                movieType,
                color = animatedColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            selectedIndex = index
                        }
                    )
            )
        }
    }
}


@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen { }
}

