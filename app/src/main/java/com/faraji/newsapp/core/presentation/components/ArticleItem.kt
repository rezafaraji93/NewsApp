package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.presentation.ui.theme.SpaceMedium
import com.faraji.newsapp.core.presentation.ui.theme.SpaceSmall

@ExperimentalFoundationApi
@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onArticleClick: () -> Unit,
    onArticleLongPress: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onArticleClick,
                onLongClick = onArticleLongPress,
            )
            .padding(SpaceMedium)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = article.urlToImage ?: "https://content.indiainfoline.com/_media/iifl/img/misc/2015-08/03/full/world-news-1438594300-977300.jpg",
                        builder = {
                            crossfade(true)
                        }


                    ),
                    contentDescription = "news image",
                    modifier = Modifier.size(width = 160.dp, height = 90.dp)
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = article.source?.name ?: "",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = article.publishedAt ?: "",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.width(SpaceSmall))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = article.title ?: "empty title",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.body1,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

}