package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen


@Composable
fun ReportFab(revealSheet : () -> Unit){
    FloatingActionButton(
        onClick = { revealSheet() },
        shape = CircleShape,
        backgroundColor = MainGreen,
        contentColor = Color.White
    ) {
        Icon(Icons.Outlined.FilterAlt, contentDescription = null)
    }
}