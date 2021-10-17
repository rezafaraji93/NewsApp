package com.faraji.newsapp.feature_news_detail.presentation

data class NewsDetailState(
    val source: String? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val newsUrl: String? = null,
    val imageUrl: String? = null,
    val publishedAt: String? = null,
    val isLoading: Boolean = true
)
