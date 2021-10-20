package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.faraji.newsapp.R
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
                                icon = bottomNavItems.icon,
                                text = bottomNavItems.text,
                                contentDescription = bottomNavItems.contentDescription,
                                selected = bottomNavItems.route == navController.currentDestination?.route,
                                enabled = bottomNavItems.icon != null
                            ) {
                                if (navController.currentDestination?.route != bottomNavItems.route)
                                    navController.navigate(bottomNavItems.route)
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