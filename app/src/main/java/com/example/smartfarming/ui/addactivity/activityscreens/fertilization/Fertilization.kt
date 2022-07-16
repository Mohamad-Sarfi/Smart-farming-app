package com.example.smartfarming.ui.addactivity.activityscreens.fertilization

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModel
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModelFactory
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.activityscreens.irrigation.IrrigationBody1
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars

@Composable
fun Fertilization(gardenName : String = "محمد"){
    val activity = LocalContext.current as Activity

    val viewModel : FertilizationViewModel =
        viewModel(factory = FertilizationViewModelFactory((activity.application as FarmApplication).repo))


    Scaffold(
        Modifier
            .background(PurpleLight)
            .fillMaxSize()
    ){
        Column(
            Modifier
                .fillMaxSize()
                .background(PurpleLight)
        ){
            ActivityTitle(gardenName = gardenName, activityName = "تغذیه", icon = painterResource(id = R.drawable.npk), Purple700)
            ActivitiesStepBars(viewModel.step.value, Purple700, Purple200)
            FertilizationBody(viewModel)
        }
    }
}

@Composable
fun FertilizationBody(viewModel: FertilizationViewModel) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
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
                androidx.compose.animation.AnimatedVisibility(
                    viewModel.step.value == 0
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DateSelector(date = viewModel.fertilizationDate.value, Purple700, PurpleLight){
                            viewModel.fertilizationDate.value = it
                        }
                        FertilizationType(viewModel)
                        FertilizerName(viewModel)
                    }
                }
            }

            // Button row **************************************************************************
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
                        backgroundColor = Purple700,
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
                        backgroundColor = Purple700,
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
fun FertilizationType(viewModel: FertilizationViewModel){
    val typeList = stringArrayResource(id = R.array.fertilization_type)
    var clicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Text(
            text = "نوع تغذیه",
            style = MaterialTheme.typography.subtitle1,
            color = Purple500,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        OutlinedButton(
            onClick = { clicked = !clicked},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = PurpleLight,
                contentColor = Purple700
            ),
            border = BorderStroke(1.dp, PurpleLight)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Purple700,
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = viewModel.fertilizationType.value,
                    style = MaterialTheme.typography.body1, color = Purple700, modifier = Modifier.padding(end = 20.dp))
            }
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(0.7f),
            expanded = clicked,
            onDismissRequest = { clicked = false }
        ) {
            typeList.forEach {
                DropdownMenuItem(
                    onClick = {
                        clicked = false
                        viewModel.fertilizationType.value = it
                    }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }

    }

}

@Composable
fun FertilizerName(viewModel: FertilizationViewModel){

    val focusManager = LocalFocusManager.current
    var clicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Text(
            text = "نام کود",
            style = MaterialTheme.typography.subtitle1,
            color = Purple500,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        OutlinedTextField(
            value = viewModel.currentFertilizerName.value,
            onValueChange = {
                clicked = true
                viewModel.currentFertilizerName.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 0.dp)
                .background(PurpleLight, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = PurpleLight,
                textColor = Purple700,
                focusedBorderColor = PurpleLight,
                unfocusedBorderColor = PurpleLight),
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            )
        )
        
        if (!viewModel.fertilizerName.value.isNullOrEmpty()){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                LazyRow{
                    items(viewModel.fertilizerName.value.size){ index ->
                        var deleted by remember {
                            mutableStateOf(false)
                        }
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(if (deleted) RedFertilizer else Purple500)
                                .clickable {
                                    deleted = true
                                    viewModel.removeFertilizer(viewModel.fertilizerName.value[index])
                                }
                                .padding(vertical = 4.dp, horizontal = 9.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text =if (deleted) "حذف شد" else viewModel.fertilizerName.value[index], color = Color.White, style = MaterialTheme.typography.subtitle1)
                        }
                    }
                }
            }
        }

        if (viewModel.currentFertilizerName.value != "" || clicked){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        viewModel.addFertilizer(viewModel.currentFertilizerName.value)

                        viewModel.currentFertilizerName.value = ""
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "افزودن کود",
                    style = MaterialTheme.typography.subtitle1,
                    color = Purple500,
                )

                Icon(Icons.Default.Add, contentDescription = null, tint = Purple500, modifier = Modifier.padding(start = 4.dp))

            }

        }

    }
}

@Composable
@Preview
fun previewFertilization(){
    Fertilization()
}