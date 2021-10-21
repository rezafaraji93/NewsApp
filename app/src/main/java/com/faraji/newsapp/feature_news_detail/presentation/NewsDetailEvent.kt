package com.faraji.newsapp.feature_news_detail.presentation

import com.faraji.newsapp.core.domain.models.Article

sealed class NewsDetailEvent {
    data class SaveArticle(val article: Article): NewsDetailEvent()
    data class isLoading(val isLoading: Boolean): NewsDetailEvent()
    data class OnProgressChanged(val progress: Float): NewsDetailEvent()
}
