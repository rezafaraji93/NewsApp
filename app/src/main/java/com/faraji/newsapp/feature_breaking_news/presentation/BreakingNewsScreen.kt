package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.presentation.ui.theme.TextGray
import com.faraji.newsapp.core.presentation.ui.theme.TextWhite
import com.faraji.newsapp.core.util.UiEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagerApi
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
    val titles = listOf("United States", "Canada", "Germany")
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        if (state.isLoadingFirstTime) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Center)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                contentColor = MaterialTheme.colors.primary
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.body1.copy(
                                    color = if (pagerState.currentPage == index) TextWhite else TextGray
                                )
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                count = titles.size,
                state = pagerState
            ) {

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
                            Divider(modifier = Modifier.background(TextGray))
                        }
                    }
                    item {
                        if (state.isLoadingNewNews)
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(CenterHorizontally)
                            )
                    }
                    newsList.apply {
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
}

