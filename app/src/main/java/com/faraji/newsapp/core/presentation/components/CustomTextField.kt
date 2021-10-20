package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.faraji.newsapp.R
import com.faraji.newsapp.core.presentation.ui.theme.IconSizeMedium
import com.faraji.newsapp.core.presentation.ui.theme.TextGray

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 40,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    onSearchAction: () -> Unit = {},
    hintColor: Color = TextGray,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isSearchScreen: Boolean = false,
    onSearchTrailingIconClicked: () -> Unit = {},
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChanged: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength)
                    onValueChanged(it)
            },
            textStyle = textStyle,
            maxLines = maxLines,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.body1.copy(
                        color = hintColor
                    )
                )
            },
            modifier = modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchAction()
                }
            ),
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                }

            } else null,
            trailingIcon = if (isSearchScreen) {
                {
                    IconButton(
                        onClick = { onSearchTrailingIconClicked() }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = stringResource(id = R.string.trailing_search_icon),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            } else null
        )


    }
}