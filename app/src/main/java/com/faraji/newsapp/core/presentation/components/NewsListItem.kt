package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.presentation.ui.theme.SpaceSmall
import com.faraji.newsapp.core.presentation.ui.theme.TextGray
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState

@ExperimentalFoundationApi
@Composable
fun NewsListItem(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    onSwipeRefresh: () -> Unit = {},
    isLoadingFirstTime: Boolean,
    isLoadingNewNews: Boolean,
    newsList: LazyPagingItems<Article>? = null,
    onArticleClicked: (Article) -> Unit = {},
    onLoadStateRefresh: () -> Unit = {},
    onLoadStateLoading: () -> Unit = {},
    onLoadStateNotLoading: () -> Unit = {},
    onLoadStateError: () -> Unit = {},
) {
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onSwipeRefresh() },
        indicator = { indicatorState, trigger ->
            SwipeRefreshIndicator(
                state = indicatorState,
                refreshTriggerDistance = trigger,
                contentColor = MaterialTheme.colors.onBackground
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (isLoadingFirstTime) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                newsList?.let { news ->
                    items(news) { articles ->
                        articles?.let { article ->
                            ArticleItem(
                                article = article,
                                onArticleClick = { onArticleClicked(article) }
                            )
                            Divider(modifier = Modifier.background(TextGray))
                        }
                    }
                    item {
                        if (isLoadingNewNews) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(SpaceSmall)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                    news.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                onLoadStateRefresh()
                            }
                            loadState.append is LoadState.Loading -> {
                                onLoadStateLoading()
                            }
                            loadState.append is LoadState.NotLoading -> {
                                onLoadStateNotLoading()
                            }
                            loadState.append is LoadState.Error -> {
                                onLoadStateError()
                            }
                        }
                    }
                }

            }
        }

    }
}