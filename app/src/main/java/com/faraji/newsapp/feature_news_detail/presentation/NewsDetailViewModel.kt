package com.faraji.newsapp.feature_news_detail.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.use_cases.UseCases
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val useCase: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _webPageState = mutableStateOf(NewsDetailState())
    val webPageState: State<NewsDetailState> = _webPageState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.apply {
            get<String>("source")?.let { source ->
                _webPageState.value = _webPageState.value.copy(
                    source = source
                )
            }

            get<String>("author")?.let { author ->
                _webPageState.value = _webPageState.value.copy(
                    author = author
                )
            }

            get<String>("title")?.let { title ->
                _webPageState.value = _webPageState.value.copy(
                    title = title
                )
            }

            get<String>("description")?.let { description ->
                _webPageState.value = _webPageState.value.copy(
                    description = description
                )
            }

            get<String>("newsUrl")?.let { url ->
                _webPageState.value = _webPageState.value.copy(
                    newsUrl = url
                )
            }

            get<String>("imageUrl")?.let { imageUrl ->
                _webPageState.value = _webPageState.value.copy(
                    imageUrl = imageUrl
                )
            }

            get<String>("publishedAt")?.let { publishedAt ->
                _webPageState.value = _webPageState.value.copy(
                    publishedAt = publishedAt
                )
            }
        }

    }

    fun onEvent(event: NewsDetailEvent) {
        when (event) {
            is NewsDetailEvent.SaveArticle -> {
                viewModelScope.launch {
                    useCase.addToFavoritesUseCase.invoke(event.article)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.article_saved_successfully)
                        )
                    )
                }
            }
            is NewsDetailEvent.OnProgressChanged -> {
                _webPageState.value = _webPageState.value.copy(
                    progress = event.progress
                )
            }
        }
    }


}