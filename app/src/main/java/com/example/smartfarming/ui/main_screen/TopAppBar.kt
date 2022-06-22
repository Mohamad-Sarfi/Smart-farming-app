package com.example.smartfarming.ui.main_screen

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addgarden.AddGarden

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun TopAppBar(){

    val activity = LocalContext.current as Activity

    var addClicked by remember {
        mutableStateOf(value = false)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .shadow(0.5.dp)
        .padding(vertical = 10.dp, horizontal = 30.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            AnimatedVisibility(
                visible = addClicked,
                enter = slideInHorizontally()
            ) {
                Text(text = "افزودن باغ", style = MaterialTheme.typography.subtitle2, color = MainGreen)
            }

            IconButton(onClick = {
                if (!addClicked){
                    addClicked = true
                } else {
                    addClicked = false
                    val intent = Intent(activity, AddGarden::class.java)
                    activity.startActivity(intent)
                }
            }) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            


        }

        IconButton(onClick = { /*TODO*/ }) {
            BadgeBox(badgeContent = { Text(text = "5")}) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier.size(30.dp)
                )
            }
        }



    }
}