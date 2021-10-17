package com.faraji.newsapp.core.util

import com.faraji.newsapp.core.util.Constants.BREAKING_NEWS_SCREEN
import com.faraji.newsapp.core.util.Constants.NEWS_DETAIL_SCREEN
import com.faraji.newsapp.core.util.Constants.SAVED_NEWS_SCREEN
import com.faraji.newsapp.core.util.Constants.SEARCH_NEWS_SCREEN
import com.faraji.newsapp.core.util.Constants.SPLASH_SCREEN

sealed class Screen(val route: String) {
    object SplashScreen : Screen(SPLASH_SCREEN)
    object BreakingNewsScreen : Screen(BREAKING_NEWS_SCREEN)
    object SavedNewsScreen : Screen(SAVED_NEWS_SCREEN)
    object SearchNewsScreen : Screen(SEARCH_NEWS_SCREEN)
    object NewsDetailScreen : Screen(NEWS_DETAIL_SCREEN)
}