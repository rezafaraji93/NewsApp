package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val useCase: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(BreakingNewsState())
    val state: State<BreakingNewsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var usNews: Flow<PagingData<Article>>? = null
    var deNews: Flow<PagingData<Article>>? = null
    var caNews: Flow<PagingData<Article>>? = null

    init {
        viewModelScope.launch {
            usNews = useCase.getBreakingNewsUseCase.invoke("us")
            caNews = useCase.getBreakingNewsUseCase.invoke("ca")
            deNews = useCase.getBreakingNewsUseCase.invoke("de")
        }
    }

    fun onEvent(event: BreakingNewsEvent) {
        when (event) {
            is BreakingNewsEvent.LoadMoreNews -> {
                _state.value = _state.value.copy(
                    isLoadingNewNews = true
                )
            }
            is BreakingNewsEvent.LoadedPage -> {
                _state.value = _state.value.copy(
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
