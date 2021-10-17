package com.faraji.newsapp.feature_splash_screen.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.faraji.newsapp.R
import com.faraji.newsapp.core.util.Constants.SPLASH_SCREEN_DURATION
import com.faraji.newsapp.core.util.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    overshootInterpolator.getInterpolation(it)
                }
            )
        )
        delay(SPLASH_SCREEN_DURATION)
        navController.popBackStack()
        navController.navigate(Screen.BreakingNewsScreen.route)
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.news),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier
                .size(120.dp)
                .scale(scale.value)
        )
    }
}