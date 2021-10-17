package com.faraji.newsapp.feature_search_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.core.presentation.components.CustomTextFieldState
import com.faraji.newsapp.feature_search_news.domain.use_cases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val useCase: SearchNewsUseCase
) : ViewModel() {

    private val _searchTextFieldState = mutableStateOf(CustomTextFieldState())
    val searchTextFieldState: State<CustomTextFieldState> = _searchTextFieldState

    private val _state = mutableStateOf(SearchNewsState())
    val state: State<SearchNewsState> = _state

    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.EnteredQuery -> {
                _searchTextFieldState.value = searchTextFieldState.value.copy(
                    text = event.query
                )
                viewModelScope.launch {
                    delay(2000L)
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    val result = useCase.invoke(searchTextFieldState.value.text, 1)
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                news = result.data?.articles,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
            is SearchNewsEvent.IsLoading -> {

            }
        }
    }
}