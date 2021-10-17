package com.faraji.newsapp.core.domain.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
    fun toNews(): News {
        return News(
            status = status,
            totalResults = totalResults,
            articles = articles
        )
    }
}