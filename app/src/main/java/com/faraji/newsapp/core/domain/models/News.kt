package com.faraji.newsapp.core.domain.models

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
