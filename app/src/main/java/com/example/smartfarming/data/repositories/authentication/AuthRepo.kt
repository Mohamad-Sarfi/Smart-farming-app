package com.example.smartfarming.data.repositories.authentication

import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.resources.user.request2JSON
import com.example.smartfarming.data.network.resources.userSignupRequest.forgetPassword2Json
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
        phoneNumber: String,
        password: String
    ) = safeApiCall {
        api.signup(
            signUpReq2JSON(password = password, phoneNumber = phoneNumber).toRequestBody("application/json".toMediaTypeOrNull())
        )
    }

    suspend fun forgetPassword(
        phoneNumber : String
    ) = api.forgetPassword(
        forgetPassword2Json(phoneNumber).toRequestBody("application/json".toMediaTypeOrNull())
    )
}