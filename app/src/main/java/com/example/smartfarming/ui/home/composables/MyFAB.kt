package com.example.smartfarming.ui.home.composables

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.AddActivities
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivity.AddActivityActivity
import com.example.smartfarming.ui.addgarden.AddGarden

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyFAB(
    context : Context,
    fabExtended : Boolean,
    type : String = "add activity",
    switchFab : () -> Unit
){

    val fabWidthAnimate by animateDpAsState(
        if(fabExtended) 150.dp else 60.dp
    )

    FloatingActionButton(
        onClick = {
            if (!fabExtended) {
                switchFab()
            }
            else{
                if (type == "add activity"){
                    val intent = Intent(context, AddActivityActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, AddGarden::class.java)
                    context.startActivity(intent)
                }
                switchFab()
            }
        },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.size(width = fabWidthAnimate, height = 60.dp),
        backgroundColor = MainGreen
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                fabExtended,
                enter = slideInHorizontally()
                        + expandHorizontally(
                    expandFrom = Alignment.End
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
            ) {
                Text(
                    text = if (type == "add activity") "ثبت فعالیت" else "افزودن باغ",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(end = 10.dp),
                    color = Color.White
                )
            }

            Icon(
                if (type == "add activity") painterResource(id = R.drawable.shovel) else painterResource(id = R.drawable.sprout_white),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .size(27.dp),
                tint = Color.White
            )

        }
    }
}