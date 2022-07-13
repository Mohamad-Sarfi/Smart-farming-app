package com.example.smartfarming.ui.addactivity.activityscreens.irrigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationLight
import com.example.smartfarming.ui.addactivities.ui.theme.LightBlue


@Composable
fun Irrigation(gardenName : String = "محمد"){

    var step by remember{
        mutableStateOf(0)
    }

    Scaffold(
        Modifier
            .background(LightBlue)
            .fillMaxSize(),
        topBar = {
            IrrigationTopBar()
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightBlue)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 30.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "باغ " +  gardenName, style = MaterialTheme.typography.h5, color = BlueIrrigation, modifier = Modifier.padding(end = 15.dp))
                Text(text = "آبیاری", style = MaterialTheme.typography.h3, color = BlueIrrigation)
            }
            StepManager(step)
            IrrigationBody(step)
        }
    }
}

@Composable
fun IrrigationBody(step: Int){
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
    ) {
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (bottomRow, body) = createRefs()

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueIrrigation,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null )
                }

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueIrrigation,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.8f)
                ) {
                    Text(text = if (step == 0 ) "بعدی" else "ثبت اطلاعات", style = MaterialTheme.typography.body2)
                }



            }
        }
    }
}

@Composable
fun StepManager(step : Int){

    val firstWidth by animateFloatAsState(
        if (step == 0 ) 0.5f else 0.4f
    )
    val secondtWidth by animateFloatAsState(
        if (step == 1 ) 0.5f else 0.4f
    )

    Row(
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(firstWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 0) BlueIrrigation else BlueIrrigationLight)

        )
        Box(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(secondtWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 1) BlueIrrigation else BlueIrrigationLight)


        )

    }
}