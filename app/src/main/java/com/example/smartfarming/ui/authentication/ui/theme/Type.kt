package com.example.smartfarming.ui.authentication.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.smartfarming.R


val sina = FontFamily(
    Font(R.font.sina)
)


// Set of Material typography styles to start with
val Typography = Typography(
    subtitle1 = TextStyle(
        fontFamily = sina,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    subtitle2 = TextStyle(
        fontFamily = sina,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    body1 = TextStyle(
        fontFamily = sina,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    body2 = TextStyle(
        fontFamily = sina,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h1 = TextStyle(
        fontFamily = sina,
        fontSize = 96.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h2 = TextStyle(
        fontFamily = sina,
        fontSize = 60.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h3 = TextStyle(
        fontFamily = sina,
        fontSize = 48.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h4 = TextStyle(
        fontFamily = sina,
        fontSize = 34.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h5 = TextStyle(
        fontFamily = sina,
        fontSize = 24.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    h6 = TextStyle(
        fontFamily = sina,
        fontSize = 20.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    caption = TextStyle(
        fontFamily = sina,
        fontSize = 12.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
    overline = TextStyle(
        fontFamily = sina,
        fontSize = 10.sp,
        textDirection = TextDirection.ContentOrRtl
    ),
)

