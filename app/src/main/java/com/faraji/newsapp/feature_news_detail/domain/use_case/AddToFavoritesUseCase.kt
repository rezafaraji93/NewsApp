package com.faraji.newsapp.feature_news_detail.domain.use_case

import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.Article

class AddToFavoritesUseCase(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(article: Article): Long {
        return repository.addToFavorites(article)
    }

}