package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun Report(
    navHostController: NavHostController,
    gardenName : String
){

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "گزارش فعالیت های", color = MainGreen, style = MaterialTheme.typography.h3)
            Text(text = "باغ محمد", color = MainGreen, style = MaterialTheme.typography.h4)
            GraphsRow()
            ColdExposureTime()
            WorkerReport()
            IrrigationReport()
            HarvestGraph(navHostController, gardenName )
            PesticideReport()
            FertilizationReport()
            OthersReport()
        }
    }
}

@Composable
fun WorkerReport(){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "کارگران استفاده شده", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "16" + " نفر", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.Person, contentDescription = null, tint = BorderGray, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun GraphsRow(){
    Row(
        Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .width(110.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp
        ) {

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 7.dp, horizontal = 3.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    progress = 0.30f,
                    color = YellowPesticide,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "نیاز سمپاشی", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle1)
            }
            Column(
                Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "30%", modifier = Modifier.padding(top = 35.dp), color = Color.Black, style = MaterialTheme.typography.body1)
            }
        }

        Card(
            modifier = Modifier
                .padding(5.dp)
                .width(110.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 7.dp, horizontal = 3.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    progress = 0.75f,
                    color = BlueIrrigation,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "نیاز آبی", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle1)
            }
            Column(
                Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "75%", modifier = Modifier.padding(top = 35.dp), color = Color.Black, style = MaterialTheme.typography.body1)
            }
        }
        Card(
            modifier = Modifier
                .padding(5.dp)
                .width(110.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 7.dp, horizontal = 3.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    progress = 0.9f,
                    color = Purple700,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "نیاز تغذیه", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle1)
            }
            Column(
                Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "90%", modifier = Modifier.padding(top = 35.dp), color = Color.Black, style = MaterialTheme.typography.body1)
            }
        }


    }
}

@Composable
fun IrrigationReport(){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد آبیاری", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "4", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = BlueIrrigation, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun PesticideReport(){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد سمپاشی", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "10", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.PestControl, contentDescription = null, tint = YellowPesticide, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun FertilizationReport() {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد تغذیه", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "16", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.Compost, contentDescription = null, tint = Purple500, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun OthersReport(){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "دیگر فعالیتها", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "8", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.Agriculture, contentDescription = null, tint = MainGreen, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun ColdExposureTime(){

    var clicked by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clickable { clicked = !clicked },
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "نیاز سرمایی باغ", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                    Text(text = "70%", style = MaterialTheme.typography.h3, color = Color.Black, )
                }
                Icon(Icons.Outlined.AcUnit, contentDescription = null, tint = BlueIrrigationDark, modifier = Modifier
                    .padding(start = 15.dp)
                    .size(55.dp))
            }
        }
    }
}

