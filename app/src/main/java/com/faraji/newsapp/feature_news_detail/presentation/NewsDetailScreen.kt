package com.faraji.newsapp.feature_news_detail.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faraji.newsapp.R
import com.faraji.newsapp.core.domain.models.Article
import com.faraji.newsapp.core.domain.models.Source
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsDetailScreen(
    navController: NavController,
    viewModel: NewsDetailViewModel = hiltViewModel()
) {

    val state = viewModel.webPageState.value
    val scope = rememberCoroutineScope()
    Log.e("TAG", "NewsDetailScreen: ${state.imageUrl} + ${state.title} ---- ${state.newsUrl}")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            val webClient = object : WebViewClient() {
                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    super.onPageStarted(view, url, favicon)
                                    viewModel.onProgressChanged(true)
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    viewModel.onProgressChanged(false)
                                }
                            }
                            webViewClient = webClient

                            settings.apply {
                                javaScriptEnabled = true
                                setSupportZoom(true)
                                javaScriptCanOpenWindowsAutomatically = true
                                domStorageEnabled = true
                            }
                            state.newsUrl?.let { url ->
                                loadUrl(url)
                            }

                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center),
                            color = Color.Blue
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 12.dp, bottom = 12.dp),
                        onClick = {
                            scope.launch {
                                state.let { article ->
                                    viewModel.onFabPressed(
                                        Article(
                                            source = Source("1", article.source ?: ""),
                                            author = article.author,
                                            title = article.title,
                                            description = article.description,
                                            url = article.newsUrl,
                                            urlToImage = article.imageUrl,
                                            publishedAt = article.publishedAt,
                                            content = ""
                                        )
                                    )
                                }
                            }

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.add_to_favorites),
                            tint = Color.White
                        )
                    }
                }
            }
        }

    }
}