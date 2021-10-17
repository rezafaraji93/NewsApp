package com.faraji.newsapp.di

import com.faraji.newsapp.core.data.remote.NewsApi
import com.faraji.newsapp.core.data.repository.NewsRepository
import com.faraji.newsapp.core.data.repository.NewsRepositoryImpl
import com.faraji.newsapp.core.util.Constants
import com.faraji.newsapp.feature_breaking_news.domain.use_cases.GetBreakingNewsUseCase
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

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
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
    fun provideRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
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

}