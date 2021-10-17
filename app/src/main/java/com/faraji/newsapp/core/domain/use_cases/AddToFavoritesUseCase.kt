package com.faraji.newsapp.core.domain.use_cases

import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.Article

class AddToFavoritesUseCase(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(article: Article): Long {
        return repository.addToFavorites(article)
    }

}