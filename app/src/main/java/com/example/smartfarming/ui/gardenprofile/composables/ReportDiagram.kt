package com.example.smartfarming.ui.gardenprofile.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    val irrigationProgress = 0.9f
    val fertilizationProgress = 0.6f
    val pesticideProgress = 0.3f

    Row(
        Modifier
            .fillMaxWidth()
            .padding(1.dp)
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
                modifier = Modifier.padding(60.dp),
                strokeWidth = 16.dp

            )

            CircularProgressIndicator(
                progress = pesticideProgress,
                color = YellowPesticide,
                modifier = Modifier.padding(30.dp),
                strokeWidth = 17.dp

            )

            CircularProgressIndicator(
                progress = fertilizationProgress,
                color = PurpleFertilizer,
                modifier = Modifier.size(40.dp),
                strokeWidth = 19.dp
            )
        }

        SideInfo(
            irrigationProgress,
            fertilizationProgress,
            pesticideProgress
            )

    }
}


@Composable
fun SideInfo(
    irrigationProgress : Float,
    fertillizationProgress : Float,
    pesticideProgress : Float,
){
    Column(
        Modifier
            .height(200.dp)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        info("نیاز آبی", BlueWatering, irrigationProgress)
        info("نیاز تغذیه", PurpleFertilizer, fertillizationProgress)
        info("سم پاشی", YellowPesticide, pesticideProgress)
    }
}


@Composable
fun info(
    name : String,
    color: Color,
    progress : Float
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.body1)
        Text(
            text = "${(progress * 100).toInt()}%",
            color = color,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 11.dp)
        )
    }
}

@Composable
@Preview
fun PreviewReportDiagram(){
    ReportDiagram()
}