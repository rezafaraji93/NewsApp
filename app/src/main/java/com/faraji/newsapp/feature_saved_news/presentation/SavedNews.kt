package com.faraji.newsapp.feature_saved_news.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faraji.newsapp.core.presentation.components.ArticleItem

@Composable
fun SavedNews(
    navController: NavController,
    viewModel: SavedNewsViewModel = hiltViewModel()
) {

    val newsList = viewModel.state.value.articles
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            newsList?.let { articles ->
                items(articles) {
                    ArticleItem(
                        article = it
                    ) {

                    }
                    Divider()
                }
            }
        }
    }
}