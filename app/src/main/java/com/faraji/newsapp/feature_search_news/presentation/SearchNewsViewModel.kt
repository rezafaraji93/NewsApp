package com.faraji.newsapp.feature_search_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.presentation.components.CustomTextFieldState
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val useCase: UseCases
) : ViewModel() {

    private val _searchTextFieldState = mutableStateOf(CustomTextFieldState())
    val searchTextFieldState: State<CustomTextFieldState> = _searchTextFieldState

    private val _state = mutableStateOf(SearchNewsState())
    val state: State<SearchNewsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.EnteredQuery -> {
                _searchTextFieldState.value = searchTextFieldState.value.copy(
                    text = event.query
                )
                getNewsForQuery(event.query)
            }
            is SearchNewsEvent.OnArticleClicked -> {
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

    private fun getNewsForQuery(query: String) {
        viewModelScope.launch {
            delay(2000L)
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result =
                useCase.searchNewsUseCase.invoke(query, 1)) {
                is Resource.Success -> {
                    if (result.data?.articles.isNullOrEmpty()) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                UiText.StringResource(R.string.no_news_found)
                            )
                        )
                    }
                    _state.value = state.value.copy(
                        news = result.data?.articles,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.no_news_found)
                        )
                    )
                }
            }
        }
    }
}