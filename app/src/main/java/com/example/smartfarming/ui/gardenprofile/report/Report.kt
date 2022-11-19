package com.example.smartfarming.ui.gardenprofile.report

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Report(
    navHostController: NavHostController,
    gardenName : String
){
    val activity = LocalContext.current as Activity
    val viewModel : ReportViewModel = hiltViewModel()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    viewModel.getAllActivitiesCount(gardenName)

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            ReportFab {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.apply {
                        if (isCollapsed) expand() else collapse()
                    }
                }
            }
        },
        sheetContent = {
            BottomSheetContent(viewModel)
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 35.dp,
        floatingActionButtonPosition = FabPosition.Center,
        sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "گزارش فعالیت های", color = MainGreen, style = MaterialTheme.typography.h5)
            Text(text = "باغ " + gardenName, color = MainGreen, style = MaterialTheme.typography.body1, modifier = Modifier.padding(bottom = 10.dp))
            GraphsRow()
            ColdExposureTime()
            WorkerReport()
            IrrigationReport(viewModel, navHostController, gardenName)
            HarvestGraph(navHostController, gardenName )
            PesticideReport(viewModel, gardenName, navHostController)
            FertilizationReport(viewModel, gardenName, navHostController)
            OthersReport(viewModel, gardenName, navHostController)
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
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "کارگران استفاده شده", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                Text(text = "16" + " نفر", style = MaterialTheme.typography.h5, color = Color.Black, )
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
                .width(100.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp
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
                        .padding(10.dp),
                )
                Text(text = "نیاز سمپاشی", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle2)
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
                .width(100.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 7.dp, horizontal = 3.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    progress = 0.75f,
                    color = Blue500,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "نیاز آبی", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle2)
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
                .width(100.dp)
                .height(140.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp
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
                Text(text = "نیاز تغذیه", modifier = Modifier.padding(top = 40.dp), color = Color.Black, style = MaterialTheme.typography.subtitle2)
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
fun IrrigationReport(viewModel: ReportViewModel, navHostController: NavHostController, gardenName: String){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 0.dp)
            .fillMaxWidth()
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable { navHostController.navigate("${AppScreensEnum.IrrigationReportScreen}/$gardenName") }
                .padding(horizontal = 40.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد آبیاری", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                Text(text = viewModel.irrigationsNumber.value.toString(), style = MaterialTheme.typography.h5, color = Color.Black, )
            }
            Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = Blue500, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun PesticideReport(viewModel: ReportViewModel, gardenName: String, navHostController: NavHostController){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 0.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable {
                    navHostController.navigate("${AppScreensEnum.PesticideReportScreen.name}/$gardenName")
                }
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد سمپاشی", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                Text(text = viewModel.pesticidesNumber.value.toString(), style = MaterialTheme.typography.h5, color = Color.Black, )
            }
            Icon(Icons.Outlined.PestControl, contentDescription = null, tint = YellowPesticide, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun FertilizationReport(viewModel: ReportViewModel, gardenName: String, navHostController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 0.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable { navHostController.navigate("${AppScreensEnum.FertilizationReportScreen.name}/$gardenName") }
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "تعداد تغذیه", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                Text(text = viewModel.fertilizationNumber.value.toString(), style = MaterialTheme.typography.h5, color = Color.Black, )
            }
            Icon(Icons.Outlined.Compost, contentDescription = null, tint = Purple500, modifier = Modifier
                .padding(start = 15.dp)
                .size(55.dp))
        }
    }
}

@Composable
fun OthersReport(viewModel: ReportViewModel, gardenName: String,navHostController: NavHostController){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 0.dp)
            .fillMaxWidth()
            .height(135.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable { navHostController.navigate("${AppScreensEnum.OthersReportScreen.name}/$gardenName") }
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "دیگر فعالیتها", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                Text(text = viewModel.otherActivitiesNumber.value.toString(), style = MaterialTheme.typography.h5, color = Color.Black, )
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
            .padding(horizontal = 5.dp, vertical = 0.dp)
            .fillMaxWidth()
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        backgroundColor = Color.White
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .clickable { clicked = !clicked },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "نیاز سرمایی باغ", style = MaterialTheme.typography.subtitle2, color = Color.Gray)
                    Text(text = "70%", style = MaterialTheme.typography.h5, color = Color.Black, )
                }
                Icon(Icons.Outlined.AcUnit, contentDescription = null, tint = BlueIrrigationDark, modifier = Modifier
                    .padding(start = 15.dp)
                    .size(55.dp))
            }
        }
    }
}

