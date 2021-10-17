package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.faraji.newsapp.core.domain.models.BottomNavItem
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
            contentDescription = "Breaking News"
        ),
        BottomNavItem(
            route = Screen.SavedNewsScreen.route,
            icon = Icons.Outlined.Save,
            contentDescription = "Breaking News"
        ),
        BottomNavItem(
            route = Screen.SearchNewsScreen.route,
            icon = Icons.Outlined.Search,
            contentDescription = "Breaking News"
        ),
    ),
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 5.dp
                ) {
                    BottomNavigation {
                        bottomNavItems.forEach { bottomNavItem ->
                            CustomBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = bottomNavItem.route == navController.currentDestination?.route,
                                enabled = bottomNavItem.icon != null
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route)
                                    navController.navigate(bottomNavItem.route)

                            }
                        }
                    }
                }
            }
        },
        scaffoldState = state,
        modifier = modifier
    ) {
        content()
    }
}