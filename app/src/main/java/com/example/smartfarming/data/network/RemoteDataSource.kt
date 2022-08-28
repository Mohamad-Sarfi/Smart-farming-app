package com.example.smartfarming.data.network

import com.example.smartfarming.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
//        private const val BASE_URL = "http://45.148.145.26:8090"
//        private const val BASE_URL = "http://10.0.2.2:9000"
//        private const val BASE_URL = "http://127.0.0.1:8000"
//        private const val BASE_URL = "http://192.168.1.58:8080"
        private const val BASE_URL = "http://192.168.1.51:8080"
    }

    fun<Api> buildApi(
        api : Class<Api>
    ) : Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG){
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}