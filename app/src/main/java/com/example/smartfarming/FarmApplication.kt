package com.example.smartfarming

import android.app.Application
import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.RemoteDataSource
import com.example.smartfarming.data.network.WeatherDataSource
import com.example.smartfarming.data.network.api.GardenApi
import com.example.smartfarming.data.network.api.WeatherApi
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import com.example.smartfarming.data.repositories.garden.GardenRemoteRepo
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.repositories.weather.WeatherRepo
import com.example.smartfarming.data.room.GardenDb
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FarmApplication : Application() {

    private val database : GardenDb by lazy { GardenDb.getDatabase(this) }

    val repo by lazy {
        GardenRepo(database.gardenDao(), database.taskDao(), database.irrigationDao(), database.harvestDao(), database.fertilizationDao(), database.pesticideDao())
    }

    protected val remoteDataSource = RemoteDataSource()
    val authRepo = AuthRepo(remoteDataSource.buildApi(AuthApi::class.java))

    val gardenRemoteRepo = GardenRemoteRepo(remoteDataSource.buildApi(GardenApi::class.java))

    protected val weatherDataSource = WeatherDataSource()
    val weatherRepo = WeatherRepo(weatherDataSource.buildApi(WeatherApi::class.java))

}