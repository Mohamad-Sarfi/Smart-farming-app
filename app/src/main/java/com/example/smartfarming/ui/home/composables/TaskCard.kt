package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.gardenprofile.GardenProfileActivity


@Composable
fun TaskCard(task : Task){

    val activity = LocalContext.current as Activity

    Card(
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                val intent = Intent(activity, GardenProfileActivity::class.java)
                intent.putExtra("gardenName", task.garden_name)
                activity.startActivity(intent)
            }
    ) {
        Image(
            painter = painterResource(id = cardBackground(task.activity_type)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.FillBounds
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            val (texts, icon) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(texts) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 55.dp)
            ) {
                Text(text = task.name, color = Color.White, style = MaterialTheme.typography.h4)
                Text(text = task.garden_name, color = Color.White, style = MaterialTheme.typography.body1)
            }
            Icon(
                painter = painterResource(taskIcon(task.activity_type)),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .constrainAs(icon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(10.dp)
                    .padding(end = 20.dp)
            )
        }
    }
}



fun cardBackground(activityType : String) : Int {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> R.drawable.back_fertilize
        ActivityTypesEnum.IRRIGATION.name -> R.drawable.back_water
        ActivityTypesEnum.PESTICIDE.name -> R.drawable.back_pest
        else -> R.drawable.back_fertilize
    }
}

fun taskIcon(activityType: String) : Int {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> R.drawable.fertilizer_line
        ActivityTypesEnum.IRRIGATION.name -> R.drawable.irrigation_line1
        ActivityTypesEnum.PESTICIDE.name -> R.drawable.pesticide_line
        else -> R.drawable.pruning_line
    }
}


@Preview
@Composable
fun TaskPreview(){
    TaskCard(task = Task(0,
        "ولک پاشی",
        activity_type = ActivityTypesEnum.FERTILIZATION.name,
        description = "به دلیل عدم تامین نیاز سرمایی",
        start_date = "",
        finish_date = "",
        garden_name = "محمد",
        recommendations = "روغن ولک",
        user_id = 5,
        seen = false
    )
    )
}
