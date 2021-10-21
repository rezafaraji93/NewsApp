package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.canada.CanadaBreakingNewsSlide
import com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.germany.GermanyBreakingNewsSlide
import com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.us.UsBreakingNewsSlide

typealias ComposableFun = @Composable () -> Unit

@ExperimentalFoundationApi
sealed class TabItem(var title: String, var screen: ComposableFun) {
    data class UsNews(
        val navController: NavController,
        val scaffoldState: ScaffoldState
    ) :
        TabItem(
            "United States",
            {
                UsBreakingNewsSlide(
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        )

    data class CaNews(
        val navController: NavController,
        val scaffoldState: ScaffoldState
    ) :
        TabItem(
            "Canada",
            {
                CanadaBreakingNewsSlide(
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        )

    data class DeNews(
        val navController: NavController,
        val scaffoldState: ScaffoldState
    ) :
        TabItem(
            "Germany",
            {
                GermanyBreakingNewsSlide(
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        )
}