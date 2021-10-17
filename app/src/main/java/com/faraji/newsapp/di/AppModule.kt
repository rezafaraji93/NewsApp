package com.faraji.newsapp.di

import android.app.Application
import com.faraji.newsapp.core.data.remote.NewsApi
import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.data.repository.NewsRepositoryImpl
import com.faraji.newsapp.core.util.Constants
import com.faraji.newsapp.db.ArticleDao
import com.faraji.newsapp.db.ArticleDatabase
import com.faraji.newsapp.feature_breaking_news.domain.use_cases.GetBreakingNewsUseCase
import com.faraji.newsapp.feature_news_detail.domain.use_case.AddToFavoritesUseCase
import com.faraji.newsapp.feature_saved_news.presentation.domain.use_case.GetSavedArticlesUseCase
import com.faraji.newsapp.feature_search_news.domain.use_cases.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        context: Application
    ): ArticleDatabase {
        return ArticleDatabase.invoke(context)
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: NewsApi, db: ArticleDao): NewsRepository {
        return NewsRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideGetBreakingNewsUseCase(repository: NewsRepository): GetBreakingNewsUseCase {
        return GetBreakingNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(repository: NewsRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddToFavoritesUseCase(repository: NewsRepository): AddToFavoritesUseCase {
        return AddToFavoritesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetSavedArticles(repository: NewsRepository): GetSavedArticlesUseCase {
        return GetSavedArticlesUseCase(repository)
    }

}