package com.faraji.newsapp.core.presentation.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsScreen
import com.faraji.newsapp.feature_news_detail.presentation.NewsDetailScreen
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
        composable(
            route = Screen.NewsDetailScreen.route.plus(
                "?source={source}&author={author}&" +
                        "title={title}&description={description}&newsUrl={newsUrl}&imageUrl={imageUrl}&publishedAt={publishedAt}"
            ),
            arguments = listOf(
                navArgument(name = "source") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "author") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "title") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "description") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "newsUrl") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "imageUrl") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(name = "publishedAt") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            NewsDetailScreen(navController = navController)
        }
    }
}