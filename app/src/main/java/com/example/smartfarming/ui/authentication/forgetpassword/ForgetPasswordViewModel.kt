package com.example.smartfarming.ui.authentication.forgetpassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : ViewModel() {
    private val phoneNumber = mutableStateOf("")

    fun setPhoneNumber(value : String){
        phoneNumber.value = value
    }

    fun getPhoneNumber() = phoneNumber.value
}