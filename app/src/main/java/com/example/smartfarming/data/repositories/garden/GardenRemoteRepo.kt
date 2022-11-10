package com.example.smartfarming.data.repositories.garden

import android.util.Log
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.data.network.api.GardenApi
import com.example.smartfarming.data.network.resources.garden_resource.request.*
import com.example.smartfarming.data.repositories.BaseRepo
import com.example.smartfarming.data.room.entities.Garden
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GardenRemoteRepo(
    private val api : GardenApi
) : BaseRepo(){

    suspend fun addGarden(
        auth : String,
        garden: Garden
    ) = safeApiCall {

        api.addGarden(
            authHeader = "Bearer $auth",
            request = garden2Json(garden).toRequestBody("application/json".toMediaTypeOrNull())
        )
    }

    suspend fun getGardens(
        auth: String,
        pageNumber : Int,
        pageSize : Int
    ) = safeApiCall {
        api.getGardens(
            authHeader = "Bearer $auth",
            pageNumber,
            pageSize
        )
    }

    suspend fun getGardenById(
        auth: String,
        id : Int
    ) = safeApiCall {
        api.getGardenById(
            auth,
            id
        )
    }

}