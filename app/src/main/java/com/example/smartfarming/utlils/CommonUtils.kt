package com.example.smartfarming.utlils

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader

class CommonUtils {
    companion object {
        inline fun <reified T> parseJsonError(throwable: HttpException): T {
            val inputStream = throwable.response()?.errorBody()?.source()?.inputStream()
            val reader: Reader = BufferedReader(InputStreamReader(inputStream))
            val gson = Gson()
            val listType = object : TypeToken<T?>() {}.type
            return gson.fromJson(reader, listType)

        }
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
            return false
        }
    }
}