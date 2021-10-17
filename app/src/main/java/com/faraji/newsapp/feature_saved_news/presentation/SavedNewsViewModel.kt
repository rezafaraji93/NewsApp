package com.faraji.newsapp.feature_saved_news.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.response.Resource
import com.faraji.newsapp.feature_saved_news.presentation.domain.use_case.GetSavedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val useCase: GetSavedArticlesUseCase
): ViewModel() {

    private val _state = mutableStateOf(SavedNewsState())
    val state: State<SavedNewsState> = _state

    init {
        viewModelScope.launch {
            val news = getNews()
            when(news){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        articles = news.data
                    )
                }
            }
        }
    }

    suspend fun getNews(): Resource<List<Article>> {
        return useCase.invoke()
    }

}