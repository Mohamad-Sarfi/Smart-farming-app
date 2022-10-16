package com.example.smartfarming.ui.gardenprofile.report.reportscreens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReportFab(color : Color, clickListener : () -> Unit) {
    FloatingActionButton(
        onClick = { clickListener() },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = color
    ) {
        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
    }
}