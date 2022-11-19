package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.forgetpassword.ForgetPasswordResponse
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.network.resources.userSignupResponse.SignupResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi{
    @POST("farmer/public/user/login/")
    suspend fun login(
        @Body request: RequestBody
    ) : LoginResponse

    @POST("farmer/public/farmer/")
    suspend fun signup(
        @Body request: RequestBody
    ) : SignupResponse

    @POST("farmer/password/forget")
    suspend fun forgetPassword(
        @Body request: RequestBody
    ) : ForgetPasswordResponse


}