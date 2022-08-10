package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.garden_resource.GardenResponse
import com.example.smartfarming.data.network.resources.user.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface GardenApi {
    @POST()
    suspend fun addGarden(
        @Body request : RequestBody
    ) : GardenResponse

    @PUT("/privileged/farmer")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body request: RequestBody
    ):LoginResponse
}