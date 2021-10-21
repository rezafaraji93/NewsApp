package com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.us

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.util.Screen
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.UiText
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsEvent
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsBreakingNewsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    var usNews: Flow<PagingData<Article>>? = null

    private val _state = mutableStateOf(BreakingNewsState())
    val state: State<BreakingNewsState> = _state

    private val _usEventFlow = MutableSharedFlow<UiEvent>()
    val usEventFlow = _usEventFlow.asSharedFlow()

    init {
        getCaBreakingNews()
    }

    private fun getCaBreakingNews() {
        viewModelScope.launch {
            usNews = useCases.getBreakingNewsUseCase.invoke("us")
        }
    }

    fun onEvent(event: BreakingNewsEvent) {
        when (event) {
            is BreakingNewsEvent.ClickedOnArticle -> {
                viewModelScope.launch {
                    _usEventFlow.emit(
                        UiEvent.Navigate(
                            Screen.NewsDetailScreen.route + "?source=${event.article.source?.name}&author=${event.article.author}&title=${event.article.title}" +
                                    "&description=${event.article.description}&newsUrl=${event.article.url}" +
                                    "&imageUrl=${event.article.urlToImage}&publishedAt=${event.article.publishedAt}"
                        )
                    )
                }
            }
            is BreakingNewsEvent.LoadMoreNews -> {
                _state.value = _state.value.copy(
                    isLoadingNewNews = true
                )
            }
            is BreakingNewsEvent.LoadedPage -> {
                _state.value = _state.value.copy(
                    isLoadingNewNews = false,
                    isLoadingFirstTime = false,
                    isRefreshing = false
                )
            }
            is BreakingNewsEvent.OnError -> {
                _state.value = _state.value.copy(
                    isLoadingNewNews = false,
                    isLoadingFirstTime = false,
                    isRefreshing = false
                )
                viewModelScope.launch {
                    _usEventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.unknownError()
                        )
                    )
                }
            }
            is BreakingNewsEvent.OnRefresh -> {
                _state.value = _state.value.copy(
                    isRefreshing = true
                )
                getCaBreakingNews()
            }
        }
    }

}