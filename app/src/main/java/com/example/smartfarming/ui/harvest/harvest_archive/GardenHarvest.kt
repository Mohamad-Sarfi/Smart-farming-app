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
            gardenName
        )


        FilterHarvest(
            modifier = Modifier
                .padding(25.dp)
                .constrainAs(filters) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )
        
        

    }
}

@Composable
fun HarvestTitle(
    modifier: Modifier,
    gardenName : String
){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = modifier) {
        Text(text = "بایگانی محصولات باغ " + gardenName , style = MaterialTheme.typography.h5, color = MainGreen)
        Icon(Icons.Default.Eco, contentDescription =null, tint = MainGreen , modifier = Modifier
            .padding(5.dp)
            .size(40.dp))
    }
}

@Composable
fun FilterHarvest(modifier: Modifier){
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
            FilterHarvestSpinner("1401", listOf("1398", "1399", "1400", "1401", "1402", "1403"))
            Icon(Icons.Default.FilterAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
            FilterHarvestSpinner("خشک", listOf("تر", "خشک", "همه"))
        }
    }
}

@Composable
fun FilterHarvestSpinner(
    title : String,
    contentList: List<String>
){
    
    var expended by remember {
        mutableStateOf(false)
    }
    
    Row(
        Modifier
            .padding(4.dp)
            .border(2.dp, Color.White, RoundedCornerShape(35.dp))
            .clip(RoundedCornerShape(35.dp))
            .clickable { expended = !expended }
            .padding(vertical = 5.dp, horizontal = 15.dp),
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
                DropdownMenuItem(onClick = { expended = false }) {
                    Text(text = item, style = MaterialTheme.typography.body1, color = MainGreen, modifier = Modifier.padding(3.dp))
                }
            }
        }
    }


}
/*

*/