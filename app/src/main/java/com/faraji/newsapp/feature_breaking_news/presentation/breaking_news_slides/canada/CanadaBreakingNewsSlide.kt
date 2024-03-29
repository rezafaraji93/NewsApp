package com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.canada

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.faraji.newsapp.core.presentation.components.NewsListItem
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.asString
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsEvent
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun CanadaBreakingNewsSlide(
    scaffoldState: ScaffoldState,
    onNewsDetailScreenClicked: (String) -> Unit,
    viewModel: CanadaBreakingNewsViewModel = hiltViewModel()
) {
    val news = viewModel.caNews?.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val swipeState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    LaunchedEffect(key1 = true) {
        viewModel.caEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    onNewsDetailScreenClicked(event.route)
                }
            }
        }
    }

    NewsListItem(
        swipeRefreshState = swipeState,
        onSwipeRefresh = { viewModel.onEvent(BreakingNewsEvent.OnRefresh) },
        isLoadingFirstTime = state.isLoadingFirstTime,
        isLoadingNewNews = state.isLoadingNewNews,
        newsList = news,
        onArticleClicked = { viewModel.onEvent(BreakingNewsEvent.ClickedOnArticle(it)) },
        onLoadStateRefresh = { viewModel.onEvent(BreakingNewsEvent.LoadedPage) },
        onLoadStateLoading = { viewModel.onEvent(BreakingNewsEvent.LoadMoreNews) },
        onLoadStateNotLoading = { viewModel.onEvent(BreakingNewsEvent.LoadedPage) },
        onLoadStateError = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Error"
                )
            }
        }
    )
}
