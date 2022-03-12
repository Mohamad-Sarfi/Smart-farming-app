package com.example.smartfarming.ui.authentication.authviewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.responses.user.LoginResponse
import com.example.smartfarming.data.network.responses.user.request2JSON
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class LoginViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {
    val phoneNumber = MutableLiveData<String>().apply {
        value = ""
    }

    val password = MutableLiveData<String>().apply {
        value = ""
    }


    // LOGIN FUNCTION
    val loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun login(){
        viewModelScope.launch {
            Log.i("req", request2JSON(password.value!!, phoneNumber.value!!))
            loginResponse.value =authRepo.login(password = password.value!!, email = phoneNumber.value!!)
        }
    }
}

class LoginViewModelFactory(
    val authRepo: AuthRepo
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}