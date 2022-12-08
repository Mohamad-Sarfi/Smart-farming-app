package com.example.smartfarming.ui.authentication.authviewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
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
    val phoneNumber = MutableLiveData<String>().apply {
        value = ""
    }
    val password = MutableLiveData<String>().apply {
        value = ""
    }
    var inProgress = mutableStateOf(false)

    // LOGIN FUNCTION
    val loginResponse = mutableStateOf<Resource<LoginResponse>?>(null)
    val gotResponse = mutableStateOf(false)

    fun login(){
        inProgress.value = true

        viewModelScope.launch {
            loginResponse.value = authRepo.login(password = password.value!!, email = phoneNumber.value!!)
            //TODo Log response here

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