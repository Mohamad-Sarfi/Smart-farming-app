package com.example.smartfarming.ui.common_composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.utils.PersianCalender
import java.util.*

@Composable
fun PersianDatePicker(
    setOpenDialog : (Boolean) -> Unit,
    updateDate: (MutableMap<String, String>) -> Unit
){
    val dateMap =  PersianCalender.getShamsiDateMap()

    var day = remember {
        mutableStateOf(dateMap["day"].toString())
    }
    var month = remember {
        mutableStateOf(dateMap["month"].toString())
    }
    var year = remember {
        mutableStateOf(dateMap["year"].toString())
    }

    var dayOfWeek = Calendar.DAY_OF_WEEK



    var weekDayName = remember {
        mutableStateOf(weekDayName(dayOfWeek))
    }

    val daysList = List<String>(31){
        (it + 1).toString()
    }

    val monthList = List<String>(12){
        (it + 1).toString()
    }

    val yearList = List<String>(10){
        (it + 1398).toString()
    }

    Dialog(onDismissRequest = { setOpenDialog(false)}) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(15.dp),
            elevation = 4.dp
        ) {
            Column(
                Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MainGreen)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Text(text = weekDayName.value, color = Color.White, style = MaterialTheme.typography.h5)
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(MainGreen.copy(0.8f))
                        .padding(25.dp)
                    ,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text =PersianCalender.getMonthNameFromNum(month.value!!.toInt()), color = Color.White, style = MaterialTheme.typography.h4 )
                    Text(text = day.value.toString(), color = Color.White, style = MaterialTheme.typography.h1)
                    Text(text = year.value.toString(), color = Color.White, style = MaterialTheme.typography.h5)
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    DateSpinner(
                        rangeList = yearList,
                        titleText = "سال",
                        defaultValue = year
                    ){ year.value = it }

                    DateSpinner(
                        rangeList = monthList,
                        titleText = "ماه",
                        defaultValue = month
                        ){ month.value = it }
                    DateSpinner(
                        rangeList = daysList,
                        titleText = "روز",
                        defaultValue = day
                    ){ day.value = it }

                }

                Text(
                    text = "تایید",
                    color = MainGreen,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .clickable {
                            Log.i("zzzz", "$dayOfWeek")
                            setOpenDialog(false)
                            updateDate(
                                mutableMapOf(
                                    "year" to year.value.toString(),
                                    "month" to month.value.toString(),
                                    "day" to day.value.toString()
                                )
                            )
                        }
                        .padding(10.dp)
                )


            }
        }
    }

}


@Composable
fun DateSpinner(
    rangeList : List<String>,
    titleText : String,
    defaultValue : MutableState<String>,
    returnValue : (String) -> Unit
){
    var current by remember {
        mutableStateOf(defaultValue.value)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .padding(vertical = 15.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .width(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightGray)
                .clickable {
                    expanded = !expanded
                }
                .padding(vertical = 8.dp, horizontal = 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = current!!,
                style = MaterialTheme.typography.subtitle1
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                rangeList.forEach{
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            current = it
                            returnValue(current!!)
                        })
                    {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )

                    }
                }
            }

        }
        Text(
            text = titleText,
            style = MaterialTheme.typography.subtitle1,
            color = MainGreen
        )
    }
}

fun weekDayName(day : Int) : String {
    return when(day){
        2 -> "یکشنبه"
        3 -> "دوشنبه"
        4 -> "سه شنبه"
        5 -> "چهارشنبه"
        6 -> "پنجشنبه"
        7 -> "جمعه"
        1 -> "شنبه"
        else -> ""

    }
}