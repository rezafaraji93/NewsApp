package com.faraji.newsapp.core.domain.use_cases

import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.News
import com.faraji.newsapp.core.domain.response.Resource

class SearchNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(query: String, pageNumber: Int): Resource<News> {
        return repository.searchForNews(query, pageNumber)
    }
}