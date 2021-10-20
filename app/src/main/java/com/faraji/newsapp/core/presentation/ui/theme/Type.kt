package com.faraji.newsapp.core.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.faraji.newsapp.R

val urbanist = FontFamily(
    Font(R.font.urbanist_bold, FontWeight.Bold),
    Font(R.font.urbanist_meduim, FontWeight.Medium),
    Font(R.font.urbanist_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    body2 = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )

)