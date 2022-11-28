package com.example.smartfarming.data.network.resources.userSignupRequest

import com.google.gson.Gson

data class UserRequest(val id: Int = 0,
                       val user: User,
                       val age: Int = 0)



fun signUpReq2JSON(
    password : String,
    phoneNumber : String,
) : String{
    val gson = Gson()
    return gson.toJson(UserRequest(
        user = User(
            password = password,
            phoneNumber = phoneNumber,
            fullName = "",
            email = "",
            language = Language(title = "FARSI"),
            shipmentAddress = ShipmentAddress(country = "Iran", plainAddress = "NaN", state = "NaN", city = "NaN"),
            address = Address(city = "NaN", state = "NaN", country = "Iran", plainAddress = "NaN"),
            bio = "NaN")
        )
    )
}

fun forgetPassword2Json(
    phoneNumber : String
) : String {
    val gson = Gson()
    return gson.toJson(phoneNumber)
}