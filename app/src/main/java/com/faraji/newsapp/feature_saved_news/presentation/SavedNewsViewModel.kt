package com.faraji.newsapp.feature_saved_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(SavedNewsState())
    val state: State<SavedNewsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            when (val result = useCases.getSavedArticlesUseCase.invoke()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        articles = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.unknownError()
                        )
                    )
                }
            }
        }

    }

    fun onEvent(event: SavedNewsEvent) {
        when (event) {
            is SavedNewsEvent.DeleteArticle -> {
                viewModelScope.launch {
                    useCases.deleteSavedArticleUseCase.invoke(event.article)
                    getNews()
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.deleted_from_saved)
                        )
                    )
                }
            }
            is SavedNewsEvent.OnArticleClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.NewsDetailScreen.route + "?source=${event.article.source?.name}&author=${event.article.author}&title=${event.article.title}" +
                                    "&description=${event.article.description}&newsUrl=${event.article.url}" +
                                    "&imageUrl=${event.article.urlToImage}&publishedAt=${event.article.publishedAt}"
                        )
                    )
                }
            }
        }
    }

}