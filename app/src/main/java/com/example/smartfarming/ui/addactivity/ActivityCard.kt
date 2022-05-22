package com.example.smartfarming.ui.addactivity

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    text : String,
    iconId : Int,
    color: Color,
    action : () -> Unit
){

    Column(
        modifier = Modifier
            .padding(10.dp)
            .size(160.dp)
            .clip(shape = MaterialTheme.shapes.large)
            .shadow(3.dp)
            .background(color)
            .clickable { action() }
            .padding(30.dp)


        ,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(5.dp)
                .size(55.dp)
                .align(Alignment.CenterHorizontally)

        )
        Text(
            text = text,
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp),
            style = MaterialTheme.typography.body2
        )
    }
}