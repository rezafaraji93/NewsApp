package com.faraji.newsapp.feature_saved_news.presentation

import com.faraji.newsapp.core.domain.models.Article

sealed class SavedNewsEvent {
    data class DeleteArticle(val article: Article) : SavedNewsEvent()
    data class OnArticleClick(val article: Article) : SavedNewsEvent()
}
