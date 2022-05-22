package com.example.smartfarming.ui.authentication.authviewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.userSignupResponse.SignupResponse
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import com.example.smartfarming.data.repositories.garden.GardenRepo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AuthViewModel(
    repo : GardenRepo,
    private val authRepo: AuthRepo
) : ViewModel() {
    val MAX_STEP = 3
    var step = mutableStateOf(0)

    val firstName = MutableLiveData<String>().apply {
        value = ""
    }
    val lastName = MutableLiveData<String>().apply {
        value = ""
    }
    val email = MutableLiveData<String>().apply {
        value = ""
    }
    val phone = MutableLiveData<String>().apply {
        value = ""
    }
    private val _password = MutableLiveData<String>().apply {
        value = ""
    }

    private val fullName : String = "${firstName.value} ${lastName.value}"
    private var age = 0
    private var state = ""
    private var city = ""


    val password : LiveData<String> = _password

    fun setPassword(pass : String){
        _password.value = pass
    }


    fun setFirstName(name : String){
        firstName.value = name
    }

    fun setLastName(name : String){
        lastName.value = name
    }

    fun setEmail(userEmail : String){
        email.value = userEmail
    }
    fun setPhone(userPhone : String){
        phone.value = userPhone
    }

    fun setState(stateName : String){
        state = stateName
    }

    fun setCity(cityName : String){
        city = cityName
    }


    val signupResponse : MutableLiveData<Resource<SignupResponse>> = MutableLiveData()

    fun signup(){
        viewModelScope.launch {
            signupResponse.value = authRepo.signup(
                email = email.value!!,
                phoneNumber = phone.value!!,
                fullName = fullName,
                password = password.value!!,
                state = state,
                city = city
            )
            if (signupResponse.value is Resource.Success){
                Log.i("signup", "${signupResponse.value}")
            }
        }
    }

}

class AuthViewModelFactory(
    val repo : GardenRepo,
    val authRepo: AuthRepo
    ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo, authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}