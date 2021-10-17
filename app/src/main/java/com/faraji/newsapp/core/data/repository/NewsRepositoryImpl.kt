package com.faraji.newsapp.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.faraji.newsapp.core.data.remote.NewsApi
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.models.News
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.feature_breaking_news.data.remote.paging.BreakingNewsSource
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override val breakingNews: Flow<PagingData<Article>>
        get() = Pager(PagingConfig(20)) {
            BreakingNewsSource(api)
        }.flow

    override suspend fun searchForNews(query: String, pageNumber: Int): Resource<News> {
        val response = api.searchForNews(query, pageNumber)
        return try {
            if (response.isSuccessful) {
                Resource.Success(response.body()?.toNews())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.message())
        } catch (e: IOException) {
            e.message?.let { msg ->
                Resource.Error(msg)
            } ?: Resource.Error("Unknown error occured!")
        }
    }
}