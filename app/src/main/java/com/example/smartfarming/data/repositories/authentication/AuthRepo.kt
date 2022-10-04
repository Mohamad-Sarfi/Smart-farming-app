package com.example.smartfarming.data.repositories.authentication

import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.resources.user.request2JSON
import com.example.smartfarming.data.network.resources.userSignupRequest.signUpReq2JSON
import com.example.smartfarming.data.repositories.BaseRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepo @Inject constructor(
    private val api : AuthApi
) : BaseRepo() {

    suspend fun login(
        email : String,
        password : String
    ) = safeApiCall{
        api.login(request2JSON(password = password, usernameOrPhoneNumber = email).toRequestBody("application/json".toMediaTypeOrNull()))
    }

    suspend fun signup(
        email : String,
        fullName : String,
        password : String,
        phoneNumber : String,
        state : String,
        city : String
    ) = safeApiCall {
        api.signup(signUpReq2JSON(email = email,
            fullName = fullName,
            password = password,
            phoneNumber = phoneNumber,
            state = state,
            city = city)
            .toRequestBody("application/json".toMediaTypeOrNull())
        )
    }
}