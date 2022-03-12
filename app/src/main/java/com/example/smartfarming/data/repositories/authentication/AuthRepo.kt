package com.example.smartfarming.data.repositories.authentication

import com.example.smartfarming.data.network.AuthApi
import com.example.smartfarming.data.network.responses.user.request2JSON
import com.example.smartfarming.data.repositories.BaseRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepo(
    private val api : AuthApi
) : BaseRepo() {

    suspend fun login(
        email : String,
        password : String
    ) = safeApiCall{
        api.login(request2JSON(password = password, usernameOrPhoneNumber = email).toRequestBody("application/json".toMediaTypeOrNull()))
    }
}