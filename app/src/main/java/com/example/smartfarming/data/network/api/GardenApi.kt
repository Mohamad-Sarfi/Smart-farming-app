package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.garden_resource.GardenResponse
import com.example.smartfarming.data.network.resources.garden_resource.request.GardenRequest
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GardenApi {

    @POST("/privileged/garden")
    suspend fun addGarden(
        @Header("Authorization") authHeader : String,
        @Body request : RequestBody
    ) : Response
}