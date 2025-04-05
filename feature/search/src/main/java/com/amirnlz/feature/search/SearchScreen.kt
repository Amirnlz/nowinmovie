package com.amirnlz.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SearchRoute(viewModel: SearchViewModel = hiltViewModel()) {
    SearchScreen()
}

@Composable
private fun SearchScreen(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Column(Modifier.padding(innerPadding)) { }
    }
}