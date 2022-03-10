package com.example.smartfarming.data.network

import com.example.smartfarming.data.network.responses.user.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface AuthApi{

    @FormUrlEncoded
    @GET("/public/user/login")
    suspend fun login(
        @Field("usernameOrPhoneNumber") email : String,
        @Field("password") password : String
    ) : LoginResponse
}