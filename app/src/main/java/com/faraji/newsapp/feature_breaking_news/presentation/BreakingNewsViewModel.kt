package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.faraji.newsapp.feature_breaking_news.domain.use_cases.GetBreakingNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    useCase: GetBreakingNewsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(BreakingNewsState())
    val state: State<BreakingNewsState> = _state

    val news = useCase.invoke()
        .cachedIn(viewModelScope)

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
        }
    }
}