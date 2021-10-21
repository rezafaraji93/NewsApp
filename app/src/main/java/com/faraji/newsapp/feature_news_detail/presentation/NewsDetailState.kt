package com.faraji.newsapp.feature_news_detail.presentation

import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.models.Source

data class NewsDetailState(
    val source: String? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val newsUrl: String? = null,
    val imageUrl: String? = null,
    val publishedAt: String? = null,
    val isLoading: Boolean = true,
    val progress: Float = 0f
) {
    fun toArticle(): Article {
        return Article(
            source = Source("", source),
            author = author,
            title = title,
            description = description,
            url = newsUrl,
            urlToImage = imageUrl,
            publishedAt = publishedAt,
            content = ""
        )
    }
}
