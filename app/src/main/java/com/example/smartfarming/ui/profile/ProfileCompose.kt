package com.example.smartfarming.ui.profile

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.data.room.entities.UserEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.ui.theme.MainGreen
import com.example.smartfarming.ui.main_screen.bottom_navigation.NAV_HOME
import com.example.smartfarming.ui.main_screen.bottom_navigation.NAV_PROFILE

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileCompose(
    navController: NavController
) {
    var context = LocalContext.current as Activity
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.ProfileViewModelFactory
            ((context.application as FarmApplication).gardenRemoteRepo)
    )
    val fullName by viewModel.fullName.observeAsState()
    val phoneNumber by viewModel.phoneNumber.observeAsState()
    val bio by viewModel.bio.observeAsState()
    val city by viewModel.cityName.observeAsState()
    val state by viewModel.stateName.observeAsState()
    val user by viewModel.user.observeAsState()
    val address by viewModel.address.observeAsState()
    val response = viewModel.editProfileResponse.observeAsState()
    val userEntity: UserEntity


    viewModel.getUserInformation()
    if (user != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())

        ) {
            header(user?.fullName!! , navController)
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .fillMaxWidth(5f)
                    .fillMaxHeight(5f)
                    .border(2.dp, colorResource(id = R.color.main_green), RoundedCornerShape(16.dp))

            ) {

                ExpandableCard(
                    title = "نام و نام خانوادگی",
                    description = user?.fullName!!,
                    icon = Icons.Default.Person
                )
                Divider(color = colorResource(id = R.color.main_green), thickness = 1.dp)
                ExpandableCard(
                    title = "شماره تلفن",
                    description = user?.phoneNumber!!,
                    icon = Icons.Default.Phone
                )
                Divider(color = colorResource(id = R.color.main_green), thickness = 1.dp)
                ExpandableCard(
                    title = "استان محل سکونت",
                    description = address?.state!!,
                    icon = Icons.Default.LocationCity
                )
                Divider(color = colorResource(id = R.color.main_green), thickness = 1.dp)
                ExpandableCard(
                    title = "شهر محل سکونت",
                    description = address?.city!!,
                    icon = Icons.Default.LocationCity
                )
                Divider(color = colorResource(id = R.color.main_green), thickness = 1.dp)
                ExpandableCard(
                    title = "بیوگرافی",
                    description = user?.bio!!,
                    icon = Icons.Default.Info
                )
            }
        }


    }


}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp,
    icon: ImageVector
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium),
                    onClick = {
                    }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
private fun header(
    name: String,
    navController: NavController
) {

    Column(
        modifier = Modifier
//            .background(colorResource(id = R.color.main_green))
            .fillMaxWidth()
    ) {
        Card(
            Modifier.align(Alignment.CenterHorizontally),
            shape = CircleShape,
            elevation = 12.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar),
                contentDescription = "User icon",
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .clip(shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                modifier = Modifier.size(40.dp),
                onClick = {

                    navController.navigate(route = AppScreensEnum.EditProfileScreen.name)
                }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = null)


            }
            Text(
                text = name,
                color = colorResource(id = R.color.main_green),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(10.dp)

            )



        }

    }
}




//@Preview
//@Composable
//fun showDemo(viewModel: ProfileViewModel) {
//    ProfileCompose(viewModel = viewModel)
//}