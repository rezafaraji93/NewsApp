package com.faraji.newsapp.feature_news_detail.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.feature_news_detail.domain.use_case.AddToFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val useCase: AddToFavoritesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _webPageState = mutableStateOf(NewsDetailState())
    val webPageState: State<NewsDetailState> = _webPageState

    init {
        savedStateHandle.apply {
            get<String>("source")?.let { source ->
                Log.e("TAG", "url: $source")
                _webPageState.value = webPageState.value.copy(
                    source = source
                )
            } ?: Log.e("TAG", "url: null source")

            get<String>("author")?.let { author ->
                Log.e("TAG", "url: $author")
                _webPageState.value = webPageState.value.copy(
                    author = author
                )
            } ?: Log.e("TAG", "url: null author")

            get<String>("title")?.let { title ->
                Log.e("TAG", "url: $title")
                _webPageState.value = webPageState.value.copy(
                    title = title
                )
            } ?: Log.e("TAG", "url: null title")

            get<String>("description")?.let { description ->
                Log.e("TAG", "url: $description")
                _webPageState.value = webPageState.value.copy(
                    description = description
                )
            } ?: Log.e("TAG", "url: null description")

            get<String>("newsUrl")?.let { url ->
                _webPageState.value = webPageState.value.copy(
                    newsUrl = url
                )
                Log.e("TAG", "imageUrl: $url ", )
            }

            get<String>("imageUrl")?.let { imageUrl ->
                _webPageState.value = webPageState.value.copy(
                    imageUrl = imageUrl
                )
                Log.e("TAG", "imageUrl: $imageUrl ", )
            }

            get<String>("publishedAt")?.let { publishedAt ->
                _webPageState.value = webPageState.value.copy(
                    publishedAt = publishedAt
                )
                Log.e("TAG", "imageUrl: $publishedAt ", )
            }
        }

    }

    fun onProgressChanged(isLoading: Boolean) {
        _webPageState.value = webPageState.value.copy(
            isLoading = isLoading
        )
    }

    suspend fun onFabPressed(article: Article){
        useCase.invoke(article)
    }

}