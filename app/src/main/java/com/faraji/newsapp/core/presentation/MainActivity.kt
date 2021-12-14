package com.faraji.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Looks
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.models.BottomNavItem
import com.faraji.newsapp.core.presentation.components.Navigation
import com.faraji.newsapp.core.presentation.ui.theme.HintGray
import com.faraji.newsapp.core.presentation.ui.theme.MediumGray
import com.faraji.newsapp.core.presentation.ui.theme.NewsAppTheme
import com.faraji.newsapp.core.util.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(MediumGray, darkIcons = useDarkIcons)
            }
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry = navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry.value?.destination?.route
                    val scaffoldState = rememberScaffoldState()
                    val bottomNavList = listOf(
                        BottomNavItem(
                            route = Screen.BreakingNewsScreen.route,
                            icon = Icons.Outlined.Article,
                            text = stringResource(id = R.string.breaking_news),
                            contentDescription = "Article",
                        ),
                        BottomNavItem(
                            route = Screen.SavedNewsScreen.route,
                            icon = Icons.Outlined.Save,
                            text = stringResource(id = R.string.saved_news),
                            contentDescription = "Saved Articles",
                        ),
                        BottomNavItem(
                            route = Screen.SearchNewsScreen.route,
                            icon = Icons.Outlined.Looks,
                            text = stringResource(id = R.string.search),
                            contentDescription = "Search news",
                        )
                    )
                    Scaffold(
                        bottomBar = {
                            if (showBottomBar(navBackStackEntry.value)) {
                                BottomAppBar(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    backgroundColor = MaterialTheme.colors.surface,
                                    cutoutShape = CircleShape,
                                    elevation = 5.dp
                                ) {
                                    BottomNavigation {
                                        bottomNavList.forEach { bottomNavItems ->
                                            BottomNavigationItem(
                                                selected = currentDestination == bottomNavItems.route,
                                                onClick = {
                                                    Navigation(navController, scaffoldState).bottomNavigation(bottomNavItems.route)
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = bottomNavItems.icon,
                                                        contentDescription = bottomNavItems.contentDescription
                                                    )
                                                },
                                                label = {
                                                    Text(text = bottomNavItems.text)
                                                },
                                                alwaysShowLabel = false,
                                                selectedContentColor = MaterialTheme.colors.primary,
                                                unselectedContentColor = HintGray
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    ) {
                        Navigation(navController, scaffoldState).Navigation()
                    }
                }
            }
        }
    }

    private fun showBottomBar(navBackStackEntry: NavBackStackEntry?): Boolean {
        return navBackStackEntry?.destination?.route in listOf(
            Screen.BreakingNewsScreen.route,
            Screen.SavedNewsScreen.route,
            Screen.SearchNewsScreen.route,
            Screen.NewsDetailScreen.route
        )
    }
}
