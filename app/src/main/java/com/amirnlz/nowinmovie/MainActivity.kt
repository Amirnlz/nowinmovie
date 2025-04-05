package com.amirnlz.nowinmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.nowinmovie.navigation.AppBottomNavigation
import com.amirnlz.nowinmovie.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NowinmovieTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AppBottomNavigation(
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(Modifier.padding(innerPadding), navController)
                }
            }
        }
    }
}
