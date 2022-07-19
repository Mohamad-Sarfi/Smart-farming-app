package com.example.smartfarming.ui.addactivity.activityscreens.others

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.viewmodels.OthersViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.OthersViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars

@Composable
fun Others(gardenName : String){

    val activity = LocalContext.current as Activity
    val viewModel : OthersViewModel =
        viewModel(factory = OthersViewModelFactory((activity.application as FarmApplication).repo))

    Scaffold(
        Modifier
            .background(LightGreen)
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGreen)
        ) {
            ActivityTitle(
                gardenName = gardenName,
                activityName = "سایر" ,
                icon = painterResource(id = R.drawable.shovel),
                color = MainGreen
            )
            ActivitiesStepBars(step = viewModel.step.value, colorDark = MainGreen, colorLight = LightGreen1)
            OthersBody(viewModel)
        }
    }
}

@Composable
fun OthersBody(viewModel: OthersViewModel){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(20.dp)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (bottomRow, body) = createRefs()
            Column(
                Modifier
                    .fillMaxSize()
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomRow.top)
                    }
            ) {
                Crossfade(viewModel.step.value, animationSpec = tween(500)) {
                    when (it) {
                        0 ->
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DateSelector(
                                    title = "فعالیت",
                                    date = viewModel.date.value,
                                    color = MainGreen,
                                    colorLight = LightGreen
                                ) {
                                    viewModel.date.value = it
                                }
                                ActivityName(viewModel)
                            }
                        1 ->
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                            }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.decreaseStep() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MainGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .height(55.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }

                Button(
                    onClick = { viewModel.increaseStep() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MainGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.8f)
                        .height(55.dp)
                ) {
                    Text(
                        text = if (viewModel.step.value == 0) "بعدی" else "ثبت اطلاعات",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

        }
    }
}


@Composable
fun ActivityName(viewModel: OthersViewModel){
    
    val otherActvitiesList = stringArrayResource(id = R.array.other_activities)
    
    var clicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "نوع فعالیت",
                style = MaterialTheme.typography.subtitle1,
                color = MainGreen
            )
            Icon(
                Icons.Outlined.Agriculture,
                contentDescription = null,
                tint = MainGreen,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(LightGreen, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    clicked = !clicked
                }
                .padding(end = 30.dp, start = 15.dp)

            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = MainGreen, modifier = Modifier.size(40.dp))
            Text(text = viewModel.activityName.value, color = MainGreen, style = MaterialTheme.typography.body1)
        }
        DropdownMenu(
            expanded = clicked,
            onDismissRequest = { clicked = false }
        ) {
            otherActvitiesList.forEach {
                DropdownMenuItem(
                    onClick = {
                        clicked = false
                        viewModel.activityName.value = it
                    }) {
                    Text(text = it, style = MaterialTheme.typography.body1, color = Color.Black)
                }
            }
        }

    }
}