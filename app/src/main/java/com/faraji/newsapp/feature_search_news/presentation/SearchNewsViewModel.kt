package com.faraji.newsapp.feature_search_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.presentation.components.CustomTextFieldState
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    var searchedNews: Flow<PagingData<Article>>? = null

    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.EnteredQuery -> {
                _searchTextFieldState.value = _searchTextFieldState.value.copy(
                    text = event.query
                )
                _state.value = _state.value.copy(
                    isLoading = true
                )
                viewModelScope.launch {
                    searchedNews =
                        useCase.searchNewsUseCase.invoke(event.query).cachedIn(viewModelScope)
                }

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
            is SearchNewsEvent.LoadMoreNews -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isLoadingNewNews = false
                )
            }
            is SearchNewsEvent.LoadedPage -> {
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
            is SearchNewsEvent.OnError -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}