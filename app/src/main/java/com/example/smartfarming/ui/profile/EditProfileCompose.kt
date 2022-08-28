package com.example.smartfarming.ui.profile

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.data.room.entities.UserEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.ui.theme.MainGreen

@Composable
fun EditProfileCompose() {
    var context = LocalContext.current as Activity
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModel.ProfileViewModelFactory
            ((context.application as FarmApplication).gardenRemoteRepo)
    )
    viewModel.getUserInformation()

    val fullName by viewModel.fullName.observeAsState()
    val phoneNumber by viewModel.phoneNumber.observeAsState()
    val bio by viewModel.bio.observeAsState()
    val city by viewModel.cityName.observeAsState()
    val state by viewModel.stateName.observeAsState()
    val user by viewModel.user.observeAsState()
    val address by viewModel.address.observeAsState()
    val response = viewModel.editProfileResponse.observeAsState()

    if(response.value != null){
        when(response.value){
            is Resource.Success -> {
                Toast.makeText(context, "اطلاعات با موفقیت بروزرسانی گردید.", Toast.LENGTH_SHORT).show()

            }
            is Resource.Failure -> {
                Toast.makeText(context, (response.value as Resource.Failure).errorMessage + "   " + (response.value as Resource.Failure).errorCode.toString(), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "خطای ناشناخته!", Toast.LENGTH_SHORT).show()

            }

        }
    }
    if (user != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())


        ) {
            header(user?.fullName!!)
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .fillMaxWidth(5f)
                    .fillMaxHeight(5f)

            ){
                body(userEntity = user!!, viewModel = viewModel, userAddress = address!!, response =response)
            }
        }
    }

}
@Composable
private fun header(
    name: String,
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
            Text(
                text = name,
                color = colorResource(id = R.color.main_green),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)

            )




    }
}
@Composable
private fun body(
    userEntity: UserEntity,
    viewModel: ProfileViewModel,
    userAddress: AddressEntity,
    response: State<Resource<LoginResponse>?>
) {
    val focus = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp)
            .fillMaxWidth(5f)
            .fillMaxHeight(5f)

    ) {

//            Card(
//                Modifier.align(Alignment.CenterHorizontally),
//                shape = RoundedCornerShape(5),
//                elevation = 12.dp
//            ) {


        OutlinedTextField(
            value = viewModel.fullName.observeAsState().value!!,

            onValueChange = {
                viewModel.fullName.value = it
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .width(300.dp)

                .padding(vertical = 10.dp),
            label = {
                Text(text = "نام و نام خانوادگی")
            },
            singleLine = true,
            maxLines = 1,

            textStyle = MaterialTheme.typography.body1,

            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "icon",
                )

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MainGreen,
                unfocusedLabelColor = MainGreen,
            )
        )
        OutlinedTextField(
            value = viewModel.phoneNumber.observeAsState().value!!,

            onValueChange = {
                viewModel.phoneNumber.value = it
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .width(300.dp)

                .padding(vertical = 10.dp),
            label = {
                Text(text = "شماره تلفن")
            },
            singleLine = true,
            maxLines = 1,

            textStyle = MaterialTheme.typography.body1,

            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = "icon",
//                        tint = if (isUsernameEmpty) MaterialTheme.colors.error else MaterialTheme.colors.primary
                )

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MainGreen,
                unfocusedLabelColor = MainGreen,
            )
        )
        OutlinedTextField(
            value = viewModel.stateName.observeAsState().value!!,

            onValueChange = {
                viewModel.stateName.value = it
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .width(300.dp)

                .padding(vertical = 10.dp),
            label = {
                Text(text = "استان محل سکونت")
            },
            singleLine = true,
            maxLines = 1,

            textStyle = MaterialTheme.typography.body1,

            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Icon(
                    Icons.Filled.LocationCity,
                    contentDescription = "icon",
                )

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MainGreen,
                unfocusedLabelColor = MainGreen,
            )
        )

        OutlinedTextField(
            value = viewModel.cityName.observeAsState().value!!,

            onValueChange = {
                viewModel.cityName.value = it
            },
//                    textStyle = TextStyle(color = Color.Black, textDirection = TextDirection.Content),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .width(300.dp)

                .padding(vertical = 10.dp),
            label = {
                Text(text = "شهر محل سکونت")
            },
            singleLine = true,
            maxLines = 1,

            textStyle = MaterialTheme.typography.body1,

            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Icon(
                    Icons.Filled.LocationCity,
                    contentDescription = "icon",
                )

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MainGreen,
                unfocusedLabelColor = MainGreen,
            )
        )
        OutlinedTextField(
            value = viewModel.bio.observeAsState().value!!,
            onValueChange = {
                viewModel.bio.value = it
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .width(300.dp)

                .padding(vertical = 10.dp),
            label = {
                Text(text = "بیوگرافی")
            },
            singleLine = true,
            maxLines = 1,

            textStyle = MaterialTheme.typography.body1,

            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "icon",
                )

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focus.moveFocus(FocusDirection.Down) }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = MainGreen,
                unfocusedLabelColor = MainGreen,
            )
        )
        Button(
            onClick = {
                viewModel.editProfile()

            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(300.dp)
                .padding(vertical = 10.dp),
            shape = MaterialTheme.shapes.medium,

            ) {
            Text(
                text = "ثبت",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
//            }


    }
}