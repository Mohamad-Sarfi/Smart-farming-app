package com.example.smartfarming.data.repositories.authentication

import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.resources.user.User
import com.example.smartfarming.data.network.resources.user.request2JSON
import com.example.smartfarming.data.network.resources.userSignupRequest.signUpReq2JSON
import com.example.smartfarming.data.repositories.BaseRepo
import com.example.smartfarming.data.room.cacheMappers.AddressCacheMapper
import com.example.smartfarming.data.room.cacheMappers.UserCacheMapper
import com.example.smartfarming.data.room.daos.UserDao
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepo(
    private val api: AuthApi,

    private val userDao: UserDao
) : BaseRepo() {
    private val userCacheMapper= UserCacheMapper()
    private val addressCacheMapper= AddressCacheMapper()
    suspend fun login(
        phoneNumber: String,
        password: String
    ) = safeApiCall {
        val login = api.login(
            request2JSON(
                password = password,
                phoneNumber = phoneNumber
            ).toRequestBody("application/json".toMediaTypeOrNull())
        )
        userDao.insertUser(userCacheMapper.mapToEntity(login.response.inRole.user))
        userDao.insertAddress(addressCacheMapper.mapToEntity(login.response.inRole.user.address!!))
        return@safeApiCall login
    }


    suspend fun signup(
        email: String,
        fullName: String,
        password: String,
        phoneNumber: String,
        state: String,
        city: String
    ) = safeApiCall {
        api.signup(
            signUpReq2JSON(
                email = email,
                fullName = fullName,
                password = password,
                phoneNumber = phoneNumber,
                state = state,
                city = city
            )
                .toRequestBody("application/json".toMediaTypeOrNull())
        )
    }
}