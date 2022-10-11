package com.example.smartfarming.ui.addactivity

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    text : String,
    icon : ImageVector,
    color: Color,
    action : () -> Unit
){
    androidx.compose.material.Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .size(160.dp)
                .background(color)
                .clickable { action() }
                .padding(30.dp),
            verticalArrangement = SpaceAround,
            horizontalAlignment = CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .size(60.dp)
            )
            Text(
                text = text,
                color = Color(0xFFFFFFFF),
                modifier = Modifier
                    .padding(top = 5.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}