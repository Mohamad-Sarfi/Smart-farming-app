package com.example.smartfarming.ui.common_composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActivitiesStepBars(step : Int, colorDark: Color, colorLight: Color){

    val firstWidth by animateFloatAsState(
        if (step == 0) .6f else .3f
    )
    val secondtWidth by animateFloatAsState(
        if (step == 1) 1f else 1f
    )

    Row(
        Modifier
            .padding(vertical = 5.dp, horizontal = 25.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Box(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(firstWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 0) colorDark else colorLight)

        )
        Box(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(secondtWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 1) colorDark else colorLight)


        )

    }
}