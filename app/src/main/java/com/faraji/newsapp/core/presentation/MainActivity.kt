package com.faraji.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.faraji.newsapp.core.presentation.components.CustomScaffold
import com.faraji.newsapp.core.presentation.components.Navigation
import com.faraji.newsapp.core.presentation.ui.theme.NewsAppTheme
import com.faraji.newsapp.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = rememberScaffoldState()
                    CustomScaffold(
                        navController = navController,
                        showBottomBar = navBackStackEntry?.destination?.route in listOf(
                            Screen.BreakingNewsScreen.route,
                            Screen.SavedNewsScreen.route,
                            Screen.SearchNewsScreen.route,
                            Screen.NewsDetailScreen.route
                        ),
                        state = scaffoldState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Navigation(navController = navController, scaffoldState = scaffoldState)
                    }
                }
            }
        }
    }
}
