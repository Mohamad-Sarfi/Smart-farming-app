package com.example.smartfarming.data.repositories

import android.util.Log
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.ErrorModel
import com.example.smartfarming.utlils.CommonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

abstract class BaseRepo {

    suspend fun <T> safeApiCall(
        apiCall : suspend () -> T
    ) : Resource<T>{
        return withContext(Dispatchers.IO){
//            Resource.Loading
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable : Throwable){
                when(throwable){
                    is HttpException -> {
                        Log.i("http error" , throwable.localizedMessage)
                        val errorModel =CommonUtils.parseJsonError<ErrorModel>(throwable)
                        Resource.Failure(false, errorModel.code,errorModel.message  )
                    }
                    else -> {
                        Log.d("error",throwable.localizedMessage +"     "+ throwable.toString())
                        Resource.Failure(true, 0 ,  "unknown error!")
                    }
                }
            }

        }
        Log.i("http error" , "something went wrong")
    }

}