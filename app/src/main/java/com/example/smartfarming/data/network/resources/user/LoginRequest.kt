package com.example.smartfarming.data.network.resources.user

import com.google.gson.Gson

data class LoginRequest(
    val password : String,
    val  phoneNumber : String
)

fun request2JSON(password: String, phoneNumber: String) : String{
    val gson = Gson()
    return gson.toJson(LoginRequest(password = password, phoneNumber = phoneNumber))
}