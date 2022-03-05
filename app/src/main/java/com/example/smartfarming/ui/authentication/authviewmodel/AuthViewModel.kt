package com.example.smartfarming.ui.authentication.authviewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarming.data.repositories.GardenRepo
import java.lang.IllegalArgumentException

class AuthViewModel(repo : GardenRepo) : ViewModel() {
    val MAX_STEP = 2
    var step = mutableStateOf(0)
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