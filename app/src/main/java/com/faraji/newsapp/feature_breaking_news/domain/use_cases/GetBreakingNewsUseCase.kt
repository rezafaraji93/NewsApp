package com.faraji.newsapp.feature_breaking_news.domain.use_cases

import androidx.paging.PagingData
import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.domain.models.Article
import kotlinx.coroutines.flow.Flow

class GetBreakingNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return repository.breakingNews
    }
}