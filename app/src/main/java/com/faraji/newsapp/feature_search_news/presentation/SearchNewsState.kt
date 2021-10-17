package com.faraji.newsapp.feature_search_news.presentation

import com.faraji.newsapp.core.domain.models.Article

data class SearchNewsState(
    val news : List<Article>? = null,
    val isLoading: Boolean = false
)