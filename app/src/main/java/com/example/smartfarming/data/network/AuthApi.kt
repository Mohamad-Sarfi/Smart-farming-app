package com.example.smartfarming.data.network

import com.example.smartfarming.data.network.responses.user.LoginRequest
import com.example.smartfarming.data.network.responses.user.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi{
    @POST("/public/user/login/")
    suspend fun login(
        @Body request: RequestBody
    ) : LoginResponse
}