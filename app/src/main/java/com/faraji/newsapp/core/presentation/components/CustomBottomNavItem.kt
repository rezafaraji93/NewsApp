package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.faraji.newsapp.core.presentation.ui.theme.HintGray

@Composable
fun RowScope.CustomBottomNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    contentDescription: String? = null,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = HintGray,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    BottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        label = {
            Text(text = text)
        },
        alwaysShowLabel = false
    )
}