package com.faraji.newsapp.feature_breaking_news.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.faraji.newsapp.core.data.remote.NewsApi
import com.faraji.newsapp.core.domain.models.Article
import okio.IOException
import retrofit2.HttpException

class UsBreakingNewsSource(
    private val api: NewsApi,
    private val countryCode: String,
) : PagingSource<Int, Article>() {

    private var currentPage = 1

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPage = params.key ?: currentPage
            val news = api.getBreakingNews(
                countryCode = countryCode,
                pageNumber = nextPage
            ).body()?.articles

            LoadResult.Page(
                data = news ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (news == null) null else currentPage + 1

            ).also {
                currentPage++
            }

        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}