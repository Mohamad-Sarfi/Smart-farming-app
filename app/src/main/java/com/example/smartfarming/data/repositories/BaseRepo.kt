package com.example.smartfarming.data.repositories

import android.util.Log
import com.example.smartfarming.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepo {

    suspend fun <T> safeApiCall(
        apiCall : suspend () -> T
    ) : Resource<T>{
        return withContext(Dispatchers.IO){
            try {
                Log.i("TAG Api", "Request sent")
                Resource.Success(apiCall.invoke())
            } catch (throwable : Throwable){
                when(throwable){
                    is HttpException -> {
                        Log.i("TAG Http exception", "$throwable")
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Log.i("TAG Other network problems", "$throwable")
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}
