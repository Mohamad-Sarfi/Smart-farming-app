package com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.MonthItem
import com.example.smartfarming.utils.MONTH_LIST

@Composable
fun TopReportCompose(title : String, gardenName: String, icon : ImageVector,color: Color, currentMonth: String, setCurrentMonth : (String) -> Unit) {

        Box(modifier = Modifier.fillMaxWidth().background(color)){
            Icon(Icons.Outlined.Compost, contentDescription = null, tint = Color.White.copy(alpha = .2f), modifier = Modifier.padding(20.dp).size(140.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    ,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End) {


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                        Text(text = title, color = Color.White, style = MaterialTheme.typography.body1)
                        Text(text = "باغ " + gardenName, color = Color.White, style = MaterialTheme.typography.h5)
                    }
                    Icon(
                        icon, contentDescription = null, tint = Color.White, modifier = Modifier
                            .padding(start = 8.dp)
                            .size(70.dp))
                }
                Row(modifier = Modifier
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp, top = 1.dp)
                    .horizontalScroll(rememberScrollState())) {
                    MONTH_LIST.forEach { month ->
                        MonthItem(name = month, currentMonth = currentMonth, color = color){setCurrentMonth(it)}
                    }
                }
            }
        }


}
