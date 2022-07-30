package com.example.smartfarming.ui.harvest.compose

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.common_composables.CommonTopBar
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.HarvestViewModelFactory

@Composable
fun HarvestCompose(
    navController: NavHostController
){
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            CommonTopBar(title = "بایگانی محصولات", Icons.Outlined.Inventory2)
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (pic, archive, add) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.harvest1),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(pic) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }
                    .width(400.dp)
                    .padding(top = 100.dp)
            )

            OutlinedButton(
                onClick = { navController.navigate(route = AppScreensEnum.ArchiveHarvestScreen.name) },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.White,
                    contentColor = MainGreen,
                ),
                border = BorderStroke(2.dp, MainGreen),
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier
                    .constrainAs(archive) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(add.top)
                    }
                    .padding(1.dp)
                    .width(240.dp)
                    .padding(bottom = 5.dp),

            ) {
                Text(
                    text = "مشاهده بایگانی",
                    style = MaterialTheme.typography.body2,
                    color = MainGreen,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Button(
                onClick = {
                          navController.navigate(route = AppScreensEnum.AddHarvestScreen.name)
                          },
                modifier = Modifier
                    .constrainAs(add) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(15.dp)
                    .width(240.dp)
                    .padding(bottom = 35.dp),
                shape = RoundedCornerShape(27.dp)
            ) {
                Icon(
                    Icons.Default.Add, contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(end = 10.dp)
                    ,

                )
                Text(
                    text = "افزودن محصول",
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

