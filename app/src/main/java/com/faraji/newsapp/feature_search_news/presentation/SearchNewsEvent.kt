package com.faraji.newsapp.feature_search_news.presentation

import com.faraji.newsapp.core.domain.models.Article

sealed class SearchNewsEvent {
    data class EnteredQuery(val query: String): SearchNewsEvent()
    data class OnArticleClicked(val article: Article): SearchNewsEvent()
}
