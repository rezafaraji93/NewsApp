package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faraji.newsapp.core.presentation.components.TabItem
import com.faraji.newsapp.core.presentation.ui.theme.TextGray
import com.faraji.newsapp.core.presentation.ui.theme.TextWhite
import com.faraji.newsapp.core.util.UiEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BreakingNewsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val titles = listOf("United States", "Canada", "Germany")

    val tabs = listOf(
        TabItem.UsNews(navController, scaffoldState),
        TabItem.CaNews(navController, scaffoldState),
        TabItem.DeNews(navController, scaffoldState)
    )
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

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Timber.e("$page")
//            when (page) {
//                0 -> {
//                    viewModel.onEvent(BreakingNewsEvent.SlidedToUsNews)
//                }
//                1 -> {
//                    viewModel.onEvent(BreakingNewsEvent.SlidedToCaNews)
//                }
//                2 -> {
//                    viewModel.onEvent(BreakingNewsEvent.SlidedToDeNews)
//                }
//            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
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
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                count = titles.size,
                state = pagerState
            ) { page ->
                tabs[page].screen()
            }
        }
    }
}

