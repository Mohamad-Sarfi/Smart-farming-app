package com.example.smartfarming.di

import com.example.smartfarming.BuildConfig
import com.example.smartfarming.data.network.api.ActivitiesApi
import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.api.GardenApi
import com.example.smartfarming.data.repositories.activities.ActivitiesRemoteRepo
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import com.example.smartfarming.data.repositories.garden.GardenRemoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        val BASE_URL = "http://kesht-afzar.ir/"

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
    }

    @Provides
    fun provideAuthService(
        retrofit: Retrofit
    ) = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideAuthRepo(
        authApi: AuthApi
    ) : AuthRepo = AuthRepo(authApi)

    @Provides
    fun provideGardenService(
        retrofit: Retrofit
    ) = retrofit.create(GardenApi::class.java)

    @Provides
    fun provideActivitiesService(
        retrofit: Retrofit
    ) = retrofit.create(ActivitiesApi::class.java)

    @Provides
    fun provideGardenRemoteRepo(
        gardenApi: GardenApi
    ) : GardenRemoteRepo = GardenRemoteRepo(gardenApi)

    @Provides
    fun provideActivitiesRemoteRepo(
        activitiesApi: ActivitiesApi
    ) : ActivitiesRemoteRepo = ActivitiesRemoteRepo(activitiesApi)
}