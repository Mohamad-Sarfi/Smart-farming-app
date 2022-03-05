package com.example.smartfarming.ui.authentication.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun RegisterTitle(modifier: Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sprout_white),
            contentDescription = "",
            modifier = Modifier
                .padding(25.dp)
                .size(120.dp),
            tint = Color.White
        )
        Text(text = "ثبت نام کاربر", style = MaterialTheme.typography.h3, color = Color.White)
    }
}