package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Looks
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.models.BottomNavItem
import com.faraji.newsapp.core.presentation.ui.theme.HintGray
import com.faraji.newsapp.core.util.Screen

@Composable
fun CustomScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    bottomNavItems: List<BottomNavItem> = listOf(
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
    ),
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surface,
                    cutoutShape = CircleShape,
                    elevation = 5.dp
                ) {
                    BottomNavigation {
                        bottomNavItems.forEach { bottomNavItems ->
                            CustomBottomNavItem(
                                selected = currentDestination == bottomNavItems.route,
                                onClick = {
                                    navController.navigate(bottomNavItems.route) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            restoreState = true
                                            launchSingleTop = true
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                    }
                                },
                            )
                            CustomBottomNavItem(
                                icon = bottomNavItems.icon,
                                text = bottomNavItems.text,
                                contentDescription = bottomNavItems.contentDescription,
                                selected = currentDestination == bottomNavItems.route,
                                enabled = bottomNavItems.icon != null
                            ) {
                                navController.navigate(bottomNavItems.route) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onBackground,
                )
            }
        },
        scaffoldState = state,
        modifier = modifier,
    ) {
        content()
    }
}