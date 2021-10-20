package com.faraji.newsapp.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.presentation.ui.theme.HintGray
import com.faraji.newsapp.core.presentation.ui.theme.SpaceMedium
import com.faraji.newsapp.core.presentation.ui.theme.SpaceSmall
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@ExperimentalFoundationApi
@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onArticleClick: () -> Unit,
    showDeleteButton: Boolean = false,
    onArticleDeletePressed: () -> Unit = {}
) {
    val loadingState = remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onArticleClick,
                onLongClick = onArticleDeletePressed,
            )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium),
        ) {
            Image(modifier = Modifier
                .size(450.dp, 200.dp)
                .clip(RoundedCornerShape(10.dp))
                .placeholder(
                    visible = loadingState.value,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = HintGray)
                ),
                painter = rememberImagePainter(
                    data = article.urlToImage
                        ?: "https://content.indiainfoline.com/_media/iifl/img/misc/2015-08/03/full/world-news-1438594300-977300.jpg",
                    builder = {
                        crossfade(true)
                        error(R.drawable.news)
                        listener(
                            onSuccess = { _: ImageRequest, _: ImageResult.Metadata ->
                                loadingState.value = false
                            }
                        )
                    }
                ),
                contentDescription = stringResource(id = R.string.news_image)

            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source?.name ?: "",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Text(
                    text = article.publishedAt ?: "",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp
                    )
                )
            }
            Divider(modifier = Modifier.padding(16.dp))
            Text(
                text = article.title ?: "empty title",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.body1,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            if (showDeleteButton) {
                Button(onClick = {
                    onArticleDeletePressed()
                }) {

                }
            }
        }
    }

}