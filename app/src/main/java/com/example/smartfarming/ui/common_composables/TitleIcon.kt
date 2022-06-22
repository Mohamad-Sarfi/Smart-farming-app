package com.example.smartfarming.ui.common_composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun TitleIcon(
    modifier : Modifier,
    icon : Painter,
    color: Color
){
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter =icon,
            contentDescription = "",
            modifier = Modifier
                .padding(10.dp)
                .size(70.dp),
            tint = color
        )
        //Text(text = "ثبت آبیاری", style = MaterialTheme.typography.h5, color = Color.White)
    }
}