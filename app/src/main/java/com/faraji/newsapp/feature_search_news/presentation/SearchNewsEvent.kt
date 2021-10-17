package com.faraji.newsapp.feature_search_news.presentation

sealed class SearchNewsEvent {
    data class EnteredQuery(val query: String): SearchNewsEvent()
    object IsLoading: SearchNewsEvent()
}
