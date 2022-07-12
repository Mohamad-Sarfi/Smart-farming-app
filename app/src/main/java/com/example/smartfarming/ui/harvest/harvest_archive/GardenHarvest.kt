package com.example.smartfarming.ui.harvest.harvest_archive

import android.app.Activity
import android.app.Application
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Filter1
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.data.network.resources.weather_response.Current
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Harvest
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.HarvestViewModelFactory
import com.example.smartfarming.utils.PersianCalender

@Composable
fun GardenHarvestScreen(gardenName: String){

    val activity = LocalContext.current as Activity
    val viewModel : HarvestViewModel = viewModel(factory = HarvestViewModelFactory((activity.application as FarmApplication).repo))




    var selectedYear = viewModel.selectedYear
    var selectedType = viewModel.selectedType

    var yearSum by remember {
        mutableStateOf(0.0)
    }

    if (selectedType.value == "همه"){
        viewModel.getHarvestByYear(gardenName, selectedYear.value)
    } else {
        viewModel.getHarvestByYearType(gardenName, selectedYear.value, selectedType.value)
    }
    val harvestList = viewModel.harvestList.observeAsState()



    if (!harvestList.value.isNullOrEmpty()){
        yearSum = viewModel.getYearSum(selectedYear.value)
    }



    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, detailColumn, filters) = createRefs()
        HarvestTitle(
            Modifier
                .padding(top = 1.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            gardenName,
            yearSum.toString(),
            selectedYear.value
        )

        HarvestListCompose(
            Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .constrainAs(detailColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                }
            ,
            harvestList = harvestList.value,
            viewModel
        )

        FilterHarvest(
            modifier = Modifier
                .padding(25.dp)
                .constrainAs(filters) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            selectedYear.value,
            selectedType.value,
            changeYear = {selectedYear.value = it},
            changeHarvestType = {selectedType.value = it}
        )
        
        

    }
}

@Composable
fun HarvestTitle(
    modifier: Modifier,
    gardenName : String,
    annualHarvest : String,
    year: String
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,) {
            Text(text = "بایگانی محصولات باغ " + gardenName , style = MaterialTheme.typography.h5, color = MainGreen)
            Icon(Icons.Default.Eco, contentDescription =null, tint = MainGreen , modifier = Modifier
                .padding(5.dp)
                .size(40.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = " کیلو در سال" + year, style = MaterialTheme.typography.body1, color = MainGreen)
            Text(text = " ${annualHarvest}", style = MaterialTheme.typography.body2, color = MainGreen)

        }

    }
}

@Composable
fun FilterHarvest(
    modifier: Modifier,
    year : String,
    harvestType : String,
    changeYear : (String) -> Unit,
    changeHarvestType : (String) -> Unit
    ){

    Card(
        elevation = 3.dp,
        modifier = modifier,
        shape = RoundedCornerShape(40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainGreen)
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterHarvestSpinner(year, listOf("1398", "1399", "1400", "1401", "1402", "1403")){changeYear(it)}
            Icon(Icons.Outlined.FilterAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
            FilterHarvestSpinner(harvestType, listOf("تر", "خشک", "همه")){changeHarvestType(it)}
        }
    }
}

@Composable
fun FilterHarvestSpinner(
    title : String,
    contentList: List<String>,
    changeState : (String) -> Unit
){
    
    var expended by remember {
        mutableStateOf(false)
    }
    
    Row(
        Modifier
            .width(130.dp)
            .border(2.dp, Color.White, RoundedCornerShape(35.dp))
            .clip(RoundedCornerShape(35.dp))
            .clickable { expended = !expended }
            .padding(vertical = 10.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.ArrowDropUp,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 5.dp)
                .size(35.dp)
        )

        Text(text = title, style = MaterialTheme.typography.body2, color = Color.White)

        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false }
        ) {
            contentList.forEach{ item ->
                DropdownMenuItem(
                    onClick = {
                        expended = false
                        changeState(item)
                    }) {
                    Text(text = item, style = MaterialTheme.typography.body1, color = MainGreen, modifier = Modifier.padding(3.dp))
                }
            }
        }
    }
}

@Composable
fun HarvestListCompose(modifier: Modifier, harvestList: List<Harvest>?, viewModel: HarvestViewModel){
    if (harvestList.isNullOrEmpty()){
        Column(
            modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "اطلاعاتی وارد نشده :(")
        }

    } else {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn{
                items(harvestList.size){ index ->
                    HarvestListItem(harvest = harvestList[index], viewModel)
                }
            }
        }
    }
}

@Composable
fun HarvestListItem(harvest: Harvest, viewModel: HarvestViewModel){
    
    var expanded by remember {
        mutableStateOf(false)
    }
    
    val height by animateDpAsState(
        if (expanded) 120.dp else 70.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    Card(
        Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth()
            .height(height)
            ,
        shape = RoundedCornerShape(25.dp),
        elevation = 2.dp,
        border = BorderStroke(2.dp, MainGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    expanded = !expanded
                }
                .padding(horizontal = 30.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${harvest.weight} kg", style = MaterialTheme.typography.h5, color = MainGreen)
                Text(
                    text = harvest.day + " " + PersianCalender.getMonthNameFromNum(harvest.month.toInt()),
                    style = MaterialTheme.typography.h5
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        deleteItem(harvest, viewModel)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "حذف", style = MaterialTheme.typography.body2, color = Color.Red, modifier = Modifier.padding(5.dp))
                Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier)
            }

        }
    }
}

fun deleteItem(harvest: Harvest, viewModel: HarvestViewModel){
    viewModel.deleteHarvestItem(harvest)
}