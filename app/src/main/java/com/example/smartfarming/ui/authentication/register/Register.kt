package com.example.smartfarming.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.register.RegisterTitle
import com.example.smartfarming.ui.authentication.register.Step1
import com.example.smartfarming.ui.authentication.register.StepCircle

@Composable
fun Register(viewModel: AuthViewModel){

    val step = viewModel.step

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (topRef, middleRef, bottomRef) = createRefs()

            RegisterTitle(
                Modifier
                    .background(MainGreen)
                    .padding(20.dp)
                    .fillMaxWidth()
                    .constrainAs(topRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

            Body(
                modifier = Modifier
                    .constrainAs(middleRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topRef.bottom)
                        bottom.linkTo(bottomRef.top)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp),
                step = step
            )

            Button(
                onClick = {
                    clickHandler(step, viewModel.MAX_STEP)
                          },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .constrainAs(bottomRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                shape = MaterialTheme.shapes.medium

            ) {
                Text(
                    text = if (step.value != viewModel.MAX_STEP) "بعدی" else "تایید",
                    style = MaterialTheme.typography.h5
                )
            }

        }
    }
}

@Composable
fun Body(modifier: Modifier, step : MutableState<Int>){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StepCircle(step = step.value, numberTag = 0, color = MainGreen)
            StepCircle(step = step.value, numberTag = 1, color = MainGreen)
            StepCircle(step = step.value, numberTag = 2, color = MainGreen)
        }
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(step.value){
                0 -> Step1(){}
            }
        }
    }
}


fun clickHandler(step : MutableState<Int>, MAX_STEP : Int){
    if (step.value < MAX_STEP){
        step.value++
    } else {
        step.value = MAX_STEP
    }
}