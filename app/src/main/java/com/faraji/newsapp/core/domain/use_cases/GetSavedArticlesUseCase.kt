package com.faraji.newsapp.core.domain.use_cases

import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.response.Resource

class GetSavedArticlesUseCase(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(): Resource<List<Article>> {
        return repository.getFavoritesNews()
    }
}