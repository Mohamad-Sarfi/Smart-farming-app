package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.garden_resource.GardenResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface GardenApi {
    @POST()
    suspend fun addGarden(
        @Body request : RequestBody
    ) : GardenResponse
}