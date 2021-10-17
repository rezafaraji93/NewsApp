package com.faraji.newsapp.feature_breaking_news.presentation

import com.faraji.newsapp.core.domain.models.Article

sealed class BreakingNewsEvent {
    object LoadMoreNews : BreakingNewsEvent()
    object LoadedPage : BreakingNewsEvent()
    data class ClickedOnArticle(val article: Article) : BreakingNewsEvent()
}