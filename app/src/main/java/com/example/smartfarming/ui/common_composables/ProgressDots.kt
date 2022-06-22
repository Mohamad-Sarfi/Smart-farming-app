package com.example.smartfarming.ui.common_composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProgressDots(
    modifier: Modifier,
    step: Int
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalStepCircle(step = step, numberTag = 0, color = Color.White)
        HorizontalStepCircle(step = step, numberTag = 1, color = Color.White)
    }
}
