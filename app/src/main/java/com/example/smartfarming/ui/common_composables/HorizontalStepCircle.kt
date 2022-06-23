package com.example.smartfarming.ui.common_composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun HorizontalStepCircle(
    step : Int,
    numberTag: Int,
    color: Color
){
    val circle1Animation by animateDpAsState(
        targetValue = if (step == numberTag) 50.dp else 16.dp
    )

    Column(modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .padding(vertical = 18.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(width = circle1Animation, height = 16.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(2.dp, color, CircleShape)
                .background(Color.White)
                .padding(14.dp)
        )
    }
}