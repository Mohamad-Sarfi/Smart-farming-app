package com.example.smartfarming.ui.harvest.harvest_archive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Filter1
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun GardenHarvestScreen(gardenName: String){

    var annualHarvest = "4500kg"

    var year by remember {
        mutableStateOf("1401")
    }

    var harvestType by remember {
        mutableStateOf("خشک")
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, annualInfo, detailColumn, filters) = createRefs()
        HarvestTitle(
            Modifier.constrainAs(title){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            },
            gardenName,
            annualHarvest,
            year
        )


        FilterHarvest(
            modifier = Modifier
                .padding(25.dp)
                .constrainAs(filters) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            year,
            harvestType,
            changeYear = {year = it},
            changeHarvestType = {harvestType = it}
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
            Text(text = "در سال " + year, style = MaterialTheme.typography.body1, color = MainGreen)
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

