package com.faraji.newsapp.core.domain.use_cases

import androidx.paging.PagingData
import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.Article
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(query: String, pageNumber: Int = 1): Flow<PagingData<Article>> {
        return repository.searchForNews(query, pageNumber)
    }
}