package com.example.smartfarming.data.repositories

import com.example.smartfarming.data.network.AuthApi

class AuthRepo(
    private val api : AuthApi
) : BaseRepo() {

    suspend fun login(
        email : String,
        password : String
    ) = safeApiCall{
        api.login(email, password)
    }
}