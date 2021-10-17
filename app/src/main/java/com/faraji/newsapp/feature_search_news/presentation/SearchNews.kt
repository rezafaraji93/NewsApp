package com.faraji.newsapp.feature_search_news.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.presentation.components.CustomTextField
import com.faraji.newsapp.core.presentation.ui.theme.SpaceSmall
import com.faraji.newsapp.core.util.Screen

@Composable
fun SearchNews(
    navController: NavController,
    viewModel: SearchNewsViewModel = hiltViewModel()
) {

    val searchFieldState = viewModel.searchTextFieldState.value
    val searchedNews = viewModel.state.value.news


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceSmall)
        ) {
            CustomTextField(
                text = searchFieldState.text,
                hint = stringResource(id = R.string.search_news_query),
                onValueChanged = {
                    viewModel.onEvent(SearchNewsEvent.EnteredQuery(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            LazyColumn {
                searchedNews?.let {

                    items(searchedNews) { article ->
                        ArticleItem(

                            article = Article(
                                source = article.source,
                                author = article.author,
                                title = article.title,
                                description = article.description,
                                url = article.url,
                                urlToImage = article.urlToImage,
                                publishedAt = article.publishedAt,
                                content = article.content
                            )

                        ) {
                            navController.navigate(Screen.SavedNewsScreen.route)
                        }
                        Divider()
                    }
                }

            }
        }
    }
}