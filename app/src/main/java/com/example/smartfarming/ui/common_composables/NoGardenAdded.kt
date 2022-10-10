package com.example.smartfarming.ui.common_composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun NoGardenAdded() {
    Column(
        Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = "",
            tint = YellowPesticide,
            modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
        )

        Text(
            text = "هنوز باغی وارد نشده!",
            style = MaterialTheme.typography.body2,
            color = BorderGray
        )
    }
}