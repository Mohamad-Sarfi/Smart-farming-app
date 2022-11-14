package com.example.smartfarming.data.network.resources.forgetpassword

data class ForgetPasswordResponse(
    val body: Body,
    val statusCode : String,
    val statusCodeValue : Int
)

data class Body(
    val s : String = "5"
)