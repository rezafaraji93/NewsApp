package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun BreakingNewsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {

    val newsList = viewModel.news.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isLoadingFirstTime) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Center)
                )
            }
            LazyColumn {
                items(newsList) { news ->
                    news?.let {
                        ArticleItem(
                            article = it,
                            onArticleClick = {
                                viewModel.onEvent(
                                    BreakingNewsEvent.ClickedOnArticle(it)
                                )
                            }
                        )
                        Divider()
                    }
                }
                item {
                    if (state.isLoadingNewNews)
                        CircularProgressIndicator(
                            modifier = Modifier.align(BottomCenter)
                        )
                }
                newsList.apply {
                    when {
                        loadState.refresh !is LoadState.Loading -> {
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
