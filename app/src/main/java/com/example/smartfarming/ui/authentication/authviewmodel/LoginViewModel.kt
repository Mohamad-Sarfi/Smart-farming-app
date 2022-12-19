package com.example.smartfarming.ui.authentication.authviewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.MainActivity
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject


class LoginViewModel constructor(
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferences
) : ViewModel() {
    val phoneNumber = mutableStateOf("")
    val password = mutableStateOf("")
    var inProgress = mutableStateOf(false)
    val isUserPassShort = mutableStateOf(false)
    val networkError = mutableStateOf(false)
    val loginSuccessful = mutableStateOf(false)
    val authError = mutableStateOf(false)

    // LOGIN FUNCTION
    val loginResponse = mutableStateOf<Resource<LoginResponse>?>(null)
    val gotResponse = mutableStateOf(false)

    fun checkUserAndPassLength() : Boolean{
        return if (phoneNumber.value.length < 11 || password.value.length < 6){
            isUserPassShort.value = true
            true
        } else {
            false
        }
    }

    fun signIn(){
        if (!checkUserAndPassLength()){
           login()
        }
    }

    fun setAllConditionsFalse(){
        loginSuccessful.value = false
        authError.value = false
        networkError.value = false
    }

    private fun setInProgress(value : Boolean){
        inProgress.value = value
    }

    fun login(){
        setInProgress(true)
        setAllConditionsFalse()

        viewModelScope.launch {
            loginResponse.value = authRepo.login(password = password.value, email = phoneNumber.value)
            Log.i("TAG Login response", "${loginResponse.value}")
            inProgress.value = false

            when(loginResponse.value) {
                is Resource.Success<*> -> {
                    loginSuccessful.value = true
//                    Toast.makeText(context, "ورود موفق", Toast.LENGTH_SHORT).show()
//                    activity.startActivity(Intent(context, MainActivity::class.java))
//                    activity.finish()
                }
                is Resource.Failure -> {
                    Log.i("TAG login response", "${loginResponse.value}")
                    authError.value = true

                    if ((loginResponse.value as Resource.Failure).errorCode == 406) {
//                        Toast.makeText(context, "این کاربر وجود ندارد", Toast.LENGTH_SHORT)
//                            .show()
                    }
//                    Toast.makeText(
//                        context,
//                        "ورود ناموفق، دوباره تلاش کنید",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
                else -> {
                    //Toast.makeText(context, "ورود ناموفق2", Toast.LENGTH_SHORT).show()
                    networkError.value = true
                    Log.i("TAG login response", "${loginResponse.value}")
                    Log.i("TAG login other error", "${loginResponse.value}")
//                    Toast.makeText(
//                        context,
//                        "!ورود ناموفق، دوباره تلاش کنید",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    loginViewModel.login()
                }
            }

            gotResponse.value = true
            inProgress.value = false

            if (loginResponse.value is Resource.Success){
                userPreferences.saveAuthToken((loginResponse.value as Resource.Success<LoginResponse>).value.response.token)
            }
        }
    }
}

class LoginViewModelFactory(
    val authRepo: AuthRepo,
    val userPreferences: UserPreferences
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepo, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}