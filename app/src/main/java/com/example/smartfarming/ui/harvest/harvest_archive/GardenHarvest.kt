package com.example.smartfarming.ui.harvest.harvest_archive

import android.app.Activity
import android.app.Application
import android.util.Log
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.data.network.resources.weather_response.Current
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Harvest
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.HarvestViewModelFactory
import com.example.smartfarming.utils.PersianCalender
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GardenHarvestScreen(gardenName: String, navHostController: NavHostController){

    val activity = LocalContext.current as Activity
    val viewModel : HarvestViewModel =
        viewModel(factory = HarvestViewModelFactory((activity.application as FarmApplication).repo))
    var selectedYear = viewModel.selectedYear
    var selectedType = viewModel.selectedType
    var yearSum by remember {
        mutableStateOf(0.0)
    }
    val harvestList = remember {
        viewModel._harvestList
    }
    val mHarvestList = viewModel.mHarvestList
    val coroutineScope = rememberCoroutineScope()

    if (selectedType.value == "همه"){
        viewModel.getHarvestByYear(gardenName)
    } else {
        viewModel.getHarvestByYearType(gardenName, selectedYear.value, selectedType.value)
    }

    if (!harvestList.value.isNullOrEmpty()){
        yearSum = viewModel.getYearSum(selectedYear.value)

        viewModel.mHarvestList.clear()

        for (e in harvestList.value){
            mHarvestList.add(e)
        }
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        topBar = { HarvestTitle(gardenName) },
        sheetContent = {
            HarvestBottomSheet(viewModel)
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 35.dp,
        floatingActionButton = {
            FabHarvest {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.apply {
                        if (isCollapsed) expand() else collapse()
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (detailColumn) = createRefs()

            HarvestListCompose(
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .constrainAs(detailColumn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                ,
                harvestList = mHarvestList,
                viewModel,
                navHostController
            )
        }
    }
}

@Composable
fun HarvestTitle(
    gardenName : String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainGreen)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "بایگانی محصولات باغ " + gardenName , style = MaterialTheme.typography.body2, color = Color.White)
        Icon(Icons.Outlined.Inventory2, contentDescription =null, tint = Color.White , modifier = Modifier
            .padding(5.dp)
            .size(35.dp)
        )
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
fun HarvestListCompose(modifier: Modifier, harvestList: List<Harvest>?, viewModel: HarvestViewModel, navHostController: NavHostController){
    if (harvestList.isNullOrEmpty()){
        Column(
            modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null, tint = YellowPesticide,
                modifier = Modifier
                    .padding(5.dp)
                    .size(55.dp))
            Text(text = "اطلاعاتی وارد نشده", style = MaterialTheme.typography.h5)
            Row(
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clickable {
                        navHostController.navigate(route = AppScreensEnum.AddHarvestScreen.name)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colors.primary, modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp))
                Text(text = "افزودن محصول", style = MaterialTheme.typography.body1)
            }
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
        if (expanded) 120.dp else 80.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    var deleteClicked by remember {
        mutableStateOf(false)
    }
    
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
                    style = MaterialTheme.typography.body1
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        deleteClicked = !deleteClicked
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "حذف", style = MaterialTheme.typography.body1, color = Color.Red, modifier = Modifier.padding(5.dp))
                Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier)
            }

        }

        if (deleteClicked){
            DeleteDialog(
                harvest,
                deleteClicked,
                viewModel,
            ){
                deleteClicked = it
            }
        }
    }
}

@Composable
private fun DeleteDialog(harvest: Harvest, deleteClicked : Boolean, viewModel: HarvestViewModel, setDeleteClicked : (Boolean) -> Unit) {

    AlertDialog(
        onDismissRequest = { setDeleteClicked(!deleteClicked) },
        buttons = {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = { setDeleteClicked(!deleteClicked) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(text = "لغو", style = MaterialTheme.typography.body2)
                }

                Button(
                    onClick = { deleteItem(harvest, viewModel) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colors.error
                    ),
                    modifier = Modifier.padding(3.dp)
                ) {
                    Text(text = "حذف", style = MaterialTheme.typography.body2)
                }

            }
        },
        title = {
            Text(text = "حذف محصول", style = MaterialTheme.typography.h5)
        },
        text = {
            Text(text ="آیا از حذف این مورد مطمئن هستید؟", style = MaterialTheme.typography.body2)
        }
    )
}

private fun deleteItem(harvest: Harvest, viewModel: HarvestViewModel){
    viewModel.mHarvestList.remove(harvest)
    viewModel.deleteHarvestItem(harvest)
}