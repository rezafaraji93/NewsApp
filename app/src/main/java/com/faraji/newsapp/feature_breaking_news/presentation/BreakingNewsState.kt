package com.faraji.newsapp.feature_breaking_news.presentation

data class BreakingNewsState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewNews: Boolean = false,
    val page: Int = 1
)