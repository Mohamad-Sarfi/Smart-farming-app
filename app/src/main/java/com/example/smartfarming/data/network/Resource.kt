package com.example.smartfarming.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

sealed class Resource<out T>{
    data class Success<out T>(val value : T) : Resource<T>()
    object Loading: Resource<Nothing>()
    data class Failure(
        val isNetworkError : Boolean = false,
        val errorCode : Int? = 0,
        val errorMessage: String= ""
//        val errorBody : ResponseBody?
    ) : Resource<Nothing>()
}
