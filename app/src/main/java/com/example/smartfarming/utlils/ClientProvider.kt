package com.example.smartfarming.utlils

import com.android.volley.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ClientProvider {
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor);
        okHttpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
        return okHttpClient.build()
    }
}