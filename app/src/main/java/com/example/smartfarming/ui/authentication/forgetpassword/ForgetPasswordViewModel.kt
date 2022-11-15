package com.example.smartfarming.ui.authentication.forgetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor() : ViewModel() {
    private val phoneNumber = mutableStateOf("")
    var showTextField = mutableStateOf(false)
    var wrongPhoneNumber = mutableStateOf(false)

    fun setPhoneNumber(value : String){
        phoneNumber.value = value
    }

    fun getPhoneNumber() = phoneNumber.value

    fun submitClickHandler(){
        if (evaluatePhoneNumber()){
            hideTextField()
        }

    }

    fun evaluatePhoneNumber() : Boolean{
        if (phoneNumber.value.length != 11){
            wrongPhoneNumber.value = true
            return false
        } else if (phoneNumber.value.substring(0, 2) != "09"){
            wrongPhoneNumber.value = true
            return false
        }
        return true
    }

    private fun hideTextField(){
        showTextField.value = false
    }
}