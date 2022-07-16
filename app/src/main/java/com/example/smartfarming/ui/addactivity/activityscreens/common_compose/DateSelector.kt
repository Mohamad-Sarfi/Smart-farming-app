package com.example.smartfarming.ui.addactivity.activityscreens.common_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationLight
import com.example.smartfarming.ui.addactivities.ui.theme.LightBlue
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel

@Composable
fun DateSelector(
    title : String,
    date : Map<String, String>,
    color: Color,
    colorLight: Color,
    setDate : (MutableMap<String, String>) -> Unit
){

    var dialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "تاریخ " + title, style = MaterialTheme.typography.subtitle1, color = color)
            Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = color, modifier = Modifier.padding(start = 10.dp))
        }

        OutlinedButton(
            onClick = {
                dialog = !dialog
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, colorLight),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorLight,
                contentColor = color
            )
        ) {
            Text(
                text =if (date["year"] == "") "تاریخ آبیاری" else "${date["year"]}/${date["month"]}/${date["day"]}",
                style = MaterialTheme.typography.body1
            )
        }

        if (dialog){
            DatePicker(openDialogue = dialog,
                changeOpenDialogue = {dialog = !dialog},
                updateDate = {date ->
                    setDate(date)
                })
        }

    }
}
