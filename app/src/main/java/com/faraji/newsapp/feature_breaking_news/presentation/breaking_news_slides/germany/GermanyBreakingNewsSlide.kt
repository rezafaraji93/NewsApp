package com.faraji.newsapp.feature_breaking_news.presentation.breaking_news_slides.germany

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.presentation.ui.theme.TextGray
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.asString
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsEvent
import com.faraji.newsapp.feature_breaking_news.presentation.BreakingNewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun GermanyBreakingNewsSlide(
    navController: NavController,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {
    val news = viewModel.deNews?.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                if (state.isLoadingFirstTime) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
            news?.let { news ->
                items(news) { articles ->
                    articles?.let { article ->
                        ArticleItem(
                            article = article,
                            onArticleClick = {
                                viewModel.onEvent(
                                    BreakingNewsEvent.ClickedOnArticle(article)
                                )
                            }
                        )
                        Divider(modifier = Modifier.background(TextGray))
                    }
                }
                item {
                    if (state.isLoadingNewNews)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        )
                }
                news.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            viewModel.onEvent(BreakingNewsEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(BreakingNewsEvent.LoadMoreNews)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(BreakingNewsEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
