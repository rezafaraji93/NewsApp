package com.faraji.newsapp.feature_breaking_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.faraji.newsapp.core.presentation.components.TabItem
import com.faraji.newsapp.core.presentation.ui.theme.TextGray
import com.faraji.newsapp.core.presentation.ui.theme.TextWhite
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun BreakingNewsScreen(
    scaffoldState: ScaffoldState,
    onDetailScreenClicked: (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val tabs = listOf(
        TabItem.UsNews(scaffoldState) {
            onDetailScreenClicked(it)
        },
        TabItem.CaNews(scaffoldState) {
            onDetailScreenClicked(it)
        },
        TabItem.DeNews(scaffoldState) {
            onDetailScreenClicked(it)
        },
    )

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
                contentColor = MaterialTheme.colors.primary,
            ) {
                tabs.forEachIndexed { index, tabItem ->
                    Tab(
                        text = {
                            Text(
                                text = tabItem.title,
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
                count = tabs.size,
                state = pagerState
            ) { page ->
                tabs[page].screen()
            }
        }
    }
}

