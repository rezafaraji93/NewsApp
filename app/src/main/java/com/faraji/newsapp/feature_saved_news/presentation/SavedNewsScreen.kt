package com.faraji.newsapp.feature_saved_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.asString
import kotlinx.coroutines.flow.collectLatest

@ExperimentalFoundationApi
@Composable
fun SavedNewsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: SavedNewsViewModel = hiltViewModel()
) {
    val newsList = viewModel.state.value.articles
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
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        LazyColumn {
            newsList?.let { articles ->
                items(articles) {
                    ArticleItem(
                        article = it,
                        onArticleClick = {
                            viewModel.onEvent(SavedNewsEvent.OnArticleClick(it))
                        },
                        onArticleDeletePressed = {
                            viewModel.onEvent(SavedNewsEvent.DeleteArticle(it))
                        }
                    )
                    Divider()
                }
            }
        }
    }
}