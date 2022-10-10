package com.example.smartfarming.ui.harvest.compose

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun FabAddHarvest(onClick : () -> Unit) {

    FloatingActionButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(Icons.Default.Add, contentDescription = "")
    }

}