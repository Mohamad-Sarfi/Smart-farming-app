package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Article
import com.example.smartfarming.ui.article.ArticleActivity
import kotlin.random.Random

@Composable
fun ArticleItem(article: Article){

    val context = LocalContext.current as Activity

    Card(modifier = Modifier
        .width(180.dp)
        .height(190.dp)
        .padding(7.dp)
        .clickable {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("article-id", article.id)
            context.startActivity(intent)
        }
        ,
        elevation = 8.dp,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
        ) {

            Image(
                painter = painterResource(id = randomIcon()),
                contentDescription = "",
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

fun randomIcon() : Int{
    return when(Random.nextInt(1, 5)){
        1 -> R.drawable.p1
        2 -> R.drawable.p2
        3 -> R.drawable.p3
        4 -> R.drawable.p4
        else -> R.drawable.p2
    }
}