package com.faraji.newsapp.feature_saved_news.presentation

import com.faraji.newsapp.core.domain.models.Article

data class SavedNewsState(
    val articles: List<Article>? = emptyList()
)