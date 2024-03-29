package com.faraji.newsapp.feature_breaking_news.presentation

import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.feature_news_detail.presentation.NewsDetailEvent

sealed class BreakingNewsEvent {
    data class SaveArticle(val article: Article): BreakingNewsEvent()
    object LoadMoreNews : BreakingNewsEvent()
    object LoadedPage : BreakingNewsEvent()
    object OnRefresh : BreakingNewsEvent()
    data class OnError(val message: String) : BreakingNewsEvent()
    data class ClickedOnArticle(val article: Article) : BreakingNewsEvent()
}