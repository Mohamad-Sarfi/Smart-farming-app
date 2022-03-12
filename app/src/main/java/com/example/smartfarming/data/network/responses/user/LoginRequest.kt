package com.example.smartfarming.data.network.responses.user

import com.google.gson.Gson

data class LoginRequest(
    val password : String,
    val  usernameOrPhoneNumber : String
)

fun request2JSON(password: String, usernameOrPhoneNumber: String) : String{
    val gson = Gson()
    return gson.toJson(LoginRequest(password = password, usernameOrPhoneNumber = usernameOrPhoneNumber))
}