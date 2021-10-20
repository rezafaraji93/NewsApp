package com.faraji.newsapp.core.data.repository

import androidx.paging.PagingData
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.response.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    val breakingNews: Flow<PagingData<Article>>

    suspend fun searchForNews(query: String, pageNumber: Int = 1): Flow<PagingData<Article>>

    suspend fun addToFavorites(article: Article): Long

    suspend fun getFavoritesNews(): Resource<List<Article>>

    suspend fun deleteFromFavorites(article: Article)
}