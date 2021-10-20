package com.faraji.newsapp.core.domain.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>? = null
)