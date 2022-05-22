package com.example.smartfarming.ui.addgarden

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.SquareFoot
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ScreensEnumActivities
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun AddGardenStep3(
    navController: NavHostController,
    isLocationSet: Boolean,
    gardenName: String,
    latLng: Map<String, String>,
    area : Int,
    setArea: (Int) -> Unit,
    setSoilType: (String) -> Unit
){
    val context = LocalContext.current
    val focus = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Icon(
            if (isLocationSet) Icons.Filled.LocationOn else Icons.Outlined.LocationOn,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    navController.navigate(ScreensEnumActivities.MapScreen.name)
                }
                .size(100.dp)
            ,
        )


        if (isLocationSet){
            Text(
                text =  "موقعیت مکانی باغ دریافت شد",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )

        }
        else {
            Text(
                text ="موقعیت باغ را مشخص کنید",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .clickable {
                        navController.navigate(ScreensEnumActivities.MapScreen.name)
                    }
                    .padding(top = 10.dp),
                color = Color.White
            )
        }

        OutlinedTextField(
            value = if (area != 0) area.toString() else "",
            onValueChange = {
                if (!it.isDigitsOnly() || it == null || it == "") setArea(0)
                else setArea(it.toInt())
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                textColor = Color.White,
                placeholderColor = Color.White,
                trailingIconColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            shape = MaterialTheme.shapes.large,
            trailingIcon = {
                Icon(Icons.Rounded.SquareFoot, contentDescription = "")
            },
            label = {
                Text(
                    "وسعت باغ",
                    style = MaterialTheme.typography.body1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focus.clearFocus()
                }
            ),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier.padding(vertical = 15.dp)
        )
        if (area != 0){
            Text(
                text = "${area.toDouble()/10000}" + " هکتار",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(3.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun SoilTypeSpinner(
    setSoilType : (String) -> Unit,
){
    var expanded by remember {
        mutableStateOf(false)
    }
    val soilTypesArray = stringArrayResource(id = R.array.soil_type)
    var soilType by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .padding(15.dp)
            .size(width = 270.dp, height = 65.dp)
            .clip(MaterialTheme.shapes.large)
            .background(LightGray)
            .clickable {
                expanded = !expanded
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        Icon(
            Icons.Filled.ArrowDropUp,
            contentDescription = "",
            tint = MainGreen
        )

        Text(
            text = if (soilType == "") "نوع خاک" else soilType,
            style = MaterialTheme.typography.body2,
            color = MainGreen,
            modifier = Modifier
                .padding(start = 90.dp, end = 5.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            soilTypesArray.forEach{
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        setSoilType(it)
                        soilType = it
                    })
                {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .padding(vertical = 3.dp, horizontal = 25.dp)
                    )

                }
            }
        }
    }

}