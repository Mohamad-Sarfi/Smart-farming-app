package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.garden_resource.request.GardenRequest
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.garden.GardenResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface GardenApi {

    @POST("farmer/privileged/garden/")
    suspend fun addGarden(
        @Header("Authorization") authHeader : String,
        @Body request : RequestBody
    ) : Garden

    @GET("privileged/garden/{pageNumber}/{pageSize}/")
    suspend fun getGardens(
        @Header("Authorization") authHeader: String,
        @Path(value = "pageNumber") pageNumber: Int,
        @Path(value = "pageSize") pageSize : Int
    ) : List<Garden>

    @GET("farmer/privileged/garden/{id}")
    suspend fun getGardenById(
        @Header("Authorization") authHeader: String,
        @Path(value = "id") id : Int
    ) : Garden
}