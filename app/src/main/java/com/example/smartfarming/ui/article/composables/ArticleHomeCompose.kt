package com.example.smartfarming.ui.article.composables

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Layers
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Article

@Composable
fun ArticleHomeCompose(article: Article) {

    val currentActivity = LocalContext.current as Activity

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (main, likeButton) = createRefs()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(main) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.p3),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(30.dp)
                        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(15.dp))
                )
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }

            LikeCompose(modifier =
            Modifier
                .constrainAs(likeButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .padding(10.dp)
                .width(90.dp)
                .padding(20.dp),
                likes = 5
            )
        }

    }
}

@Composable
fun LikeCompose(modifier: Modifier ,likes : Int){

    var likeState by remember{
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Favorite,
            tint = if (likeState) Color.Red else Color.Gray,
            contentDescription = "",
            modifier = Modifier
                .clickable {
                    likeState = !likeState
                }
                .size(65.dp)

        )
        Text(
            text = likes.toString(),
            style = MaterialTheme.typography.subtitle2
        )
    }
}