package com.faraji.newsapp.feature_breaking_news.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
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
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.util.Screen
import kotlinx.coroutines.launch

@Composable
fun BreakingNewsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {

    val newsList = viewModel.news.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
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
                        article = Article(
                            source = it.source,
                            author = it.author,
                            title = it.title,
                            description = it.description,
                            url = it.url,
                            urlToImage = it.urlToImage,
                            publishedAt = it.publishedAt,
                            content = it.content
                        )
                    ) {
                        navController.navigate(
                            Screen.NewsDetailScreen.route + "?source=${news.source?.name}&author=${news.author}&title=${news.title}" +
                                    "&description=${news.description}&newsUrl=${news.url}" +
                                    "&imageUrl=${news.urlToImage}&publishedAt=${news.publishedAt}"
                        )
                    }
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
