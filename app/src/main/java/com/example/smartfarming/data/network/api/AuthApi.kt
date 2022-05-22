package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.network.resources.userSignupResponse.SignupResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi{
    @POST("/public/user/login/")
    suspend fun login(
        @Body request: RequestBody
    ) : LoginResponse

    @POST("/public/farmer/")
    suspend fun signup(
        @Body request: RequestBody
    ) : SignupResponse

}