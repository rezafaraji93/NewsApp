package com.faraji.newsapp.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.faraji.newsapp.core.data.remote.NewsApi
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.core.util.Constants.NEWS_PAGE_SIZE
import com.faraji.newsapp.db.ArticleDao
import com.faraji.newsapp.feature_breaking_news.data.remote.paging.BreakingNewsSource
import com.faraji.newsapp.feature_search_news.data.remote.paging.SearchNewsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val db: ArticleDao
) : NewsRepository {

    override val breakingNews: Flow<PagingData<Article>>
        get() = Pager(PagingConfig(NEWS_PAGE_SIZE)) {
            BreakingNewsSource(api)
        }.flow

    override suspend fun searchForNews(query: String, pageNumber: Int): Flow<PagingData<Article>> {

        return Pager(PagingConfig(NEWS_PAGE_SIZE)) {
            SearchNewsSource(api, query)
        }.flow
//        return try {
//            if (response.isSuccessful) {
//                Resource.Success(response.body()?.toNews())
//            } else {
//                Resource.Error(response.message())
//            }
//        } catch (e: HttpException) {
//            Resource.Error(e.message())
//        } catch (e: IOException) {
//            e.message?.let { msg ->
//                Resource.Error(msg)
//            } ?: Resource.Error("Unknown error occured!")
//        }
    }

    override suspend fun addToFavorites(article: Article): Long {
        return db.upsert(article)
    }

    override suspend fun getFavoritesNews(): Resource<List<Article>> {
        val result = db.getAllArticles()
        return Resource.Success(result)
    }

    override suspend fun deleteFromFavorites(article: Article) {
        return db.deleteArticle(article)
    }
}