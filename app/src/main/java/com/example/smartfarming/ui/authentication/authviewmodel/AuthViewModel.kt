package com.example.smartfarming.ui.authentication.authviewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarming.data.repositories.GardenRepo
import java.lang.IllegalArgumentException

class AuthViewModel(repo : GardenRepo) : ViewModel() {
    val MAX_STEP = 2
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


}

class AuthViewModelFactory(val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}