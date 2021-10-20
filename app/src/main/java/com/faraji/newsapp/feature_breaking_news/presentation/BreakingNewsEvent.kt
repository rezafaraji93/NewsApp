package com.faraji.newsapp.feature_breaking_news.presentation

import com.faraji.newsapp.core.domain.models.Article

sealed class BreakingNewsEvent {
    object LoadMoreNews : BreakingNewsEvent()
    object LoadedPage : BreakingNewsEvent()
    object SlidedToUsNews: BreakingNewsEvent()
    object SlidedToCaNews: BreakingNewsEvent()
    object SlidedToDeNews: BreakingNewsEvent()
    data class OnError(val message: String): BreakingNewsEvent()
    data class ClickedOnArticle(val article: Article) : BreakingNewsEvent()
}