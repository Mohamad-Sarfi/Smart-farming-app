package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.resources.garden_resource.request.GardenRequest
import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.example.smartfarming.data.room.entities.IrrigationEntity
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivitiesApi {

    //Fertilization
    @POST("farmer/privileged/fertilization")
    suspend fun addFertilization(
        @Header("Authoriztion") authHeader: String,
        @Body request: RequestBody
    ) : FertilizationEntity

    @GET("farmer/privileged/fertilization/byGardenId/{gardenId}/{pageNumber}/{pageSize}")
    suspend fun getFertilizationByPage(
        @Header("Authorization") authHeader: String,
        @Path("gardenId") gardenId : Int,
        @Path("pageNumber") pageNumber : Int,
        @Path("pageSize") pageSize : Int
    ) : List<FertilizationEntity>

    @GET("farmer/privileged/fertilization/byId/{id}")
    suspend fun getFertilizationById(
        @Header("Authorization") authHeader: String,
        @Path("id") fertilizationId : Int
    ) : FertilizationEntity


    //Irrigation
    @POST("farmer/privileged/irrigation")
    suspend fun addIrrigation(
        @Header("Authorization") authHeader: String,
        @Body request: RequestBody
    ) : IrrigationEntity
}