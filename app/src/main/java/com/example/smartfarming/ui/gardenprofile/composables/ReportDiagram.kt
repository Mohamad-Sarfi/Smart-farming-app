package com.example.smartfarming.ui.gardenprofile.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun ReportDiagram(){

    val animateProgress by animateFloatAsState(
        targetValue = 0.6f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    var selected by remember {
        mutableStateOf("")
    }
    val irrigationProgress = 0.9f
    val fertilizationProgress = 0.6f
    val pesticideProgress = 0.3f

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            Modifier
                .width(200.dp)
                .height(202.dp)
                .padding(10.dp),
            elevation = 0.dp,
            backgroundColor = LightBackground
        ) {
            CircularProgressIndicator(
                progress = irrigationProgress,
                color = BlueWatering,
                modifier = Modifier
                    .padding(60.dp)
                    .clickable { selected = "irrigation" },
                strokeWidth = if (selected == "irrigation") 21.dp else 16.dp
            )

            CircularProgressIndicator(
                progress = pesticideProgress,
                color = YellowPesticide,
                modifier = Modifier
                    .padding(30.dp)
                    .clickable { selected = "pesticide" },
                strokeWidth = if (selected == "pesticide") 22.dp else 17.dp
            )

            CircularProgressIndicator(
                progress = fertilizationProgress,
                color = PurpleFertilizer,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { selected = "fertilization" },
                strokeWidth = if (selected == "fertilization") 23.dp else 19.dp
            )
        }

        SideInfo(irrigationProgress,
            fertilizationProgress,
            pesticideProgress,
            selected){selected = it}

    }
}


@Composable
fun SideInfo(
    irrigationProgress : Float,
    fertillizationProgress : Float,
    pesticideProgress : Float,
    selected : String,
    setSelected : (String) -> Unit
){
    Column(
        Modifier
            .height(200.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        info("نیاز آبی", BlueWatering, irrigationProgress, selected){setSelected(it)}
        info("نیاز تغذیه", PurpleFertilizer, fertillizationProgress, selected){setSelected(it)}
        info("سم پاشی", YellowPesticide, pesticideProgress,selected){setSelected(it)}
    }
}

@Composable
private fun info(
    name : String,
    color: Color,
    progress : Float,
    selected: String,
    setSelected: (String) -> Unit
){
    Card(
        elevation = if (selected == getTag(name)) 1.dp else 0.dp,
        backgroundColor = if (selected == getTag(name)) color else Color.Transparent,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .clickable { setSelected(getTag(name)) }
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.body1, color = if (selected == getTag(name)) Color.White else MaterialTheme.colors.onBackground)
            Text(
                text = "${(progress * 100).toInt()}%",
                color = if (selected == getTag(name)) Color.White else color,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 11.dp)
            )
        }
    }
}

private fun getTag(name : String) : String{
    return when(name){
        "نیاز آبی" -> "irrigation"
        "نیاز تغذیه" -> "fertilization"
        "سم پاشی" -> "pesticide"
        else -> ""
    }
}

@Composable
@Preview
fun PreviewReportDiagram(){
    ReportDiagram()
}