package com.faraji.newsapp.core.presentation.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsScreen
import com.faraji.newsapp.feature_news_detail.presentation.NewsDetail
import com.faraji.newsapp.feature_saved_news.presentation.SavedNews
import com.faraji.newsapp.feature_search_news.presentation.SearchNews
import com.faraji.newsapp.feature_splash_screen.presentation.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.BreakingNewsScreen.route) {
            BreakingNewsScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.SavedNewsScreen.route) {
            SavedNews(navController = navController)
        }
        composable(Screen.SearchNewsScreen.route) {
            SearchNews(navController = navController)
        }
        composable(Screen.NewsDetailScreen.route) {
            NewsDetail(navController = navController)
        }
    }
}