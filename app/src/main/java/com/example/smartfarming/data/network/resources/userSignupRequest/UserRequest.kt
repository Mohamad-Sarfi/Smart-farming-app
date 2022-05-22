package com.example.smartfarming.data.network.resources.userSignupRequest

import com.google.gson.Gson

data class UserRequest(val id: Int = 0,
                       val user: User,
                       val age: Int = 0)



fun signUpReq2JSON(
    email : String,
    fullName : String,
    password : String,
    phoneNumber : String,
    state : String,
    city : String
) : String{
    val gson = Gson()
    return gson.toJson(UserRequest(
        user = User(
            password = password,
            phoneNumber = phoneNumber,
            fullName = fullName,
            email = email,
            language = Language(title = "FARSI"),
            shipmentAddress = ShipmentAddress(country = "Iran", plainAddress = "NaN", state = "semnan", city = "damghan"),
            address = Address(city = "damghan", state = "semnan", country = "Iran", plainAddress = "Nan"),
            bio = "NaN"
        )
    )
    )
}