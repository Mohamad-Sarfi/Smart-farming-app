package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Article
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addgarden.AddGarden
import com.example.smartfarming.ui.home.HomeViewModel
import com.example.smartfarming.ui.home.HomeViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeCompose(){

    val context = LocalContext.current

    val activity = LocalContext.current as Activity

    val viewModel : HomeViewModel = viewModel(factory = HomeViewModelFactory((activity.application as FarmApplication).repo))

    val gardensList by viewModel.getGardens().observeAsState()


    val tasks = listOf<Task>(
        Task(0,
            "ولک پاشی",
            activity_type = ActivityTypesEnum.FERTILIZATION.name,
            description = "به دلیل عدم تامین نیاز سرمایی",
            start_date = "",
            finish_date = "",
            garden_name = "محمد",
            recommendations = "روغن ولک",
            user_id = 5
        ),
        Task(0,
            "سم پاشی",
            activity_type = ActivityTypesEnum.PESTICIDE.name,
            description = "مبارزه با پسیل",
            start_date = "",
            finish_date = "",
            garden_name = "محمد",
            recommendations = "روغن ولک",
            user_id = 5
        )
        ,
        Task(0,
            "آبیاری اسفند",
            activity_type = ActivityTypesEnum.IRRIGATION.name,
            description = "موعد آبیاری اسفند",
            start_date = "",
            finish_date = "",
            garden_name = "محمد",
            recommendations = "",
            user_id = 5
        )
        ,
        Task(0,
            "کود دامی",
            activity_type = ActivityTypesEnum.FERTILIZATION.name,
            description = "با توجه به ماده عالی خاک نیاز به تامین کود دامی",
            start_date = "",
            finish_date = "",
            garden_name = "اکبری",
            recommendations = "کود گاو",
            user_id = 5
        )
    )


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End

            ){
                ManageGardenPreview(gardensList, context, tasks)
            }
            FarmingArticlesPreview(articlesList = listOf())
        }
    }
}

@Composable
fun ManageGardenPreview(gardenList : List<Garden>?, context : Context, tasks : List<Task>){

    if (gardenList.isNullOrEmpty()){
        // When there are no gardens added yet, this will be displayed
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, AddGarden::class.java)
                    context.startActivity(intent)
                }
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sprout),
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .size(110.dp)
            )

            Text(
                text = "اولین باغ خود را اضافه کنید",
                style = MaterialTheme.typography.body2,
                color = MainGreen
            )
        }
    } else {
        // In case gardens are added
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "باغداری شما",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 10.dp),
                    color = MainGreen
                )
                Icon(
                    Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = MainGreen
                )
            }
            LazyColumn(){
                items(gardenList){
                    com.example.smartfarming.ui.home.composables.GardenCardHome(garden = it, tasks = tasks)
                }
        }
    }
}

@Composable
fun FarmingArticlesPreview(articlesList : List<Article>?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
        ,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "توصیه های باغداری",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp),
                color = MainGreen
            )

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "",
                tint = MainGreen
            )
        }

        LazyRow(){
            item(){
                ArticleItem(
                    article = Article(0, "آبیاری نهال پسته", "لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد. کتابهای زیادی در شصت و سه درصد گذشته، حال و آینده شناخت فراوان جامعه و متخصصان را می طلبد تا با نرم افزارها شناخت بیشتری را برای طراحان رایانه ای علی الخصوص طراحان خلاقی و فرهنگ پیشرو در زبان فارسی ایجاد کرد. در این صورت می توان امید داشت که تمام و دشواری موجود در ارائه راهکارها و شرایط سخت تایپ به پایان رسد وزمان مورد نیاز شامل حروفچینی دستاوردهای اصلی و جوابگوی سوالات پیوسته اهل دنیای موجود طراحی اساسا مورد استفاده قرار گیرد.", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
            item(){
                ArticleItem(article = Article(0, "سم پاشی فروردین", "", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
            item(){
                ArticleItem(article = Article(0, "سم پاشی فروردین", "", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
        }

    }

}

