package com.faraji.newsapp.feature_breaking_news.presentation

sealed class BreakingNewsEvent {
    object LoadMoreNews: BreakingNewsEvent()
    object LoadedPage: BreakingNewsEvent()
}