package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    useCase: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(BreakingNewsState())
    val state: State<BreakingNewsState> = _state

    val news = useCase.getBreakingNewsUseCase.invoke()
        .cachedIn(viewModelScope)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: BreakingNewsEvent) {
        when (event) {
            is BreakingNewsEvent.LoadMoreNews -> {
                _state.value = state.value.copy(
                    isLoadingNewNews = true
                )
            }
            is BreakingNewsEvent.LoadedPage -> {
                _state.value = state.value.copy(
                    isLoadingNewNews = false,
                    isLoadingFirstTime = false
                )
            }
            is BreakingNewsEvent.ClickedOnArticle -> {
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