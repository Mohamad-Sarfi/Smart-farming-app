package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun Report(){

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)
                .padding(vertical = 30.dp, horizontal = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "گزارش فعالیت های", color = MainGreen, style = MaterialTheme.typography.h3)
            Text(text = "باغ محمد", color = MainGreen, style = MaterialTheme.typography.h4)
            GraphsRow()
            WorkerReport()
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
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "کارگران استفاده شده", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                Text(text = "16" + " نفر", style = MaterialTheme.typography.h3, color = Color.Black, )
            }
            Icon(Icons.Outlined.Person, contentDescription = null, tint = BorderGray, modifier = Modifier.padding(start = 15.dp).size(55.dp))
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
@Preview
fun PreviewReport(){
    Report()
}