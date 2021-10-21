package com.faraji.newsapp.feature_search_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.faraji.newsapp.R
import com.faraji.newsapp.core.presentation.components.ArticleItem
import com.faraji.newsapp.core.presentation.components.CustomTextField
import com.faraji.newsapp.core.presentation.ui.theme.SpaceSmall
import com.faraji.newsapp.core.presentation.ui.theme.TextWhite
import com.faraji.newsapp.core.util.UiEvent
import com.faraji.newsapp.core.util.asString
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SearchNewsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: SearchNewsViewModel = hiltViewModel()
) {

    val searchFieldState = viewModel.searchTextFieldState.value
    val state = viewModel.state.value
    val searchedNews = viewModel.searchedNews?.collectAsLazyPagingItems()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isEmptyResult = remember {
        mutableStateOf(false)
    }

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
            .padding(bottom = 56.dp),
        contentAlignment = Center
    ) {
        if (searchFieldState.text.isBlank()) {
            IfSearchFieldIsEmpty()
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (isEmptyResult.value) {
            NoNewsFound()
        }
        if (state.isLoadingNewNews) {
            CircularProgressIndicator(modifier = Modifier.align(BottomCenter))
        }
    }

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
            },
            isSearchScreen = true,
            onSearchTrailingIconClicked = {
                keyboardController?.hide()
                Timber.e(searchFieldState.text)
                viewModel.onEvent(SearchNewsEvent.SearchForNews(searchFieldState.text))
            },
            onSearchAction = {
                keyboardController?.hide()
                Timber.e(searchFieldState.text)
                viewModel.onEvent(SearchNewsEvent.SearchForNews(searchFieldState.text))
            },
        )
        Spacer(modifier = Modifier.height(SpaceSmall))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            searchedNews?.let {
                items(it) { article ->
                    if (article != null) {
                        ArticleItem(
                            article = article,
                            onArticleClick = {
                                viewModel.onEvent(SearchNewsEvent.OnArticleClicked(article))
                            }
                        )
                        Divider()
                    }
                    if (it.itemCount == 0) {
                        isEmptyResult.value = true
                    }
                }
                searchedNews.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            viewModel.onEvent(SearchNewsEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(SearchNewsEvent.LoadMoreNews)
                        }
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(SearchNewsEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            viewModel.onEvent(SearchNewsEvent.OnError)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IfSearchFieldIsEmpty(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.search_for_news),
        style = MaterialTheme.typography.h1.copy(
            color = MaterialTheme.colors.primary
        ),
        modifier = modifier
    )
}

@Composable
fun NoNewsFound() {
    Text(
        text = stringResource(id = R.string.no_articles),
        style = MaterialTheme.typography.h2.copy(
            color = TextWhite
        )
    )
}
