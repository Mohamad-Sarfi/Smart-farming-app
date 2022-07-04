package com.example.smartfarming.ui.harvest.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun DateSelectHarvest(
    harvestDate : MutableState<MutableMap<String, String>>,
    setHarvestDate : (MutableMap<String, String>) -> Unit
){
    var dialogue by remember {
        mutableStateOf(false)
    }



    Button(
        onClick = { dialogue = !dialogue },
        modifier = Modifier
            .padding(20.dp)
            .width(300.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.large)
            .border(2.dp, color = MainGreen, shape = MaterialTheme.shapes.large),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White
        )
    ) {
        Text(
            text = if (harvestDate.value["year"] == "") "تاریخ آبیاری" else "${harvestDate.value["day"]} / ${harvestDate.value["month"]} / ${harvestDate.value["year"]}",
            style = MaterialTheme.typography.body2,
            color = BorderGray,
            modifier = Modifier.padding(6.dp)
        )
    }

    if (dialogue){
        DatePicker(openDialogue = dialogue,
            changeOpenDialogue = {dialogue = !dialogue},
            updateDate = {date ->
                setHarvestDate(date)
            })
    }
}