package com.example.smartfarming

import android.app.Application
import com.example.smartfarming.data.network.api.AuthApi
import com.example.smartfarming.data.network.RemoteDataSource
import com.example.smartfarming.data.repositories.authentication.AuthRepo
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.GardenDb
import com.example.smartfarming.data.room.cacheMappers.AddressCacheMapper
import com.example.smartfarming.data.room.cacheMappers.UserCacheMapper
import com.example.smartfarming.data.room.daos.UserDao

class FarmApplication : Application() {

    private val database : GardenDb by lazy { GardenDb.getDatabase(this) }

    val repo by lazy {
        GardenRepo(database.gardenDao(), database.taskDao(), database.irrigationDao())
    }

    protected val remoteDataSource = RemoteDataSource()
    val authRepo by lazy {

        AuthRepo(remoteDataSource.buildApi(AuthApi::class.java), database.userDao())
    }

}