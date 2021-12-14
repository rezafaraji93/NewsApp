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
        val scaffoldState: ScaffoldState,
        val navigationFunction: (String) -> Unit
    ) :
        TabItem(
            "United States",
            {
                UsBreakingNewsSlide(
                    scaffoldState = scaffoldState,
                    onNewsDetailScreenClicked = navigationFunction
                )
            }
        )

    data class CaNews(
        val scaffoldState: ScaffoldState,
        val navigationFunction: (String) -> Unit
    ) :
        TabItem(
            "Canada",
            {
                CanadaBreakingNewsSlide(
                    scaffoldState = scaffoldState,
                    onNewsDetailScreenClicked = navigationFunction
                )
            }
        )

    data class DeNews(
        val scaffoldState: ScaffoldState,
        val navigationFunction: (String) -> Unit
    ) :
        TabItem(
            "Germany",
            {
                GermanyBreakingNewsSlide(
                    scaffoldState = scaffoldState,
                    onNewsDetailScreenClicked = navigationFunction
                )
            }
        )
}