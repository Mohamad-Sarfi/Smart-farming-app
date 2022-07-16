package com.example.smartfarming.ui.addactivity.activityscreens.common_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark
import com.example.smartfarming.ui.addactivities.ui.theme.LightBlue
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.authentication.ui.theme.sina

@Composable
fun WorkerNumber(
    wNumber : Int,
    color: Color,
    colorLight: Color,
    setNumber : (Int) -> Unit
){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "تعداد کارگر", style = MaterialTheme.typography.subtitle1, color = color)
            Icon(Icons.Outlined.Person, contentDescription = null, tint = color, modifier = Modifier.padding(start = 3.dp))
        }

        OutlinedTextField(
            value = wNumber.toString() + " نفر" ,
            onValueChange = {
                setNumber(it.toInt())
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 0.dp)
                .background(colorLight, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorLight,
                textColor = color,
                focusedBorderColor = colorLight,
                trailingIconColor = color,
                leadingIconColor = color,
                unfocusedBorderColor = colorLight,


                ),
            leadingIcon = {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { if (wNumber > 1) setNumber(wNumber - 1) }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { setNumber(wNumber + 1) }
                )
            },
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
        )

    }
}