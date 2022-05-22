package com.example.smartfarming.data.repositories.garden

import com.example.smartfarming.data.network.api.GardenApi
import com.example.smartfarming.data.network.resources.garden_resource.request.BorderItem
import com.example.smartfarming.data.network.resources.garden_resource.request.GardenRequest
import com.example.smartfarming.data.network.resources.garden_resource.request.SpecieSetItem
import com.example.smartfarming.data.network.resources.garden_resource.request.gardenReq2Json
import com.example.smartfarming.data.repositories.BaseRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GardenRemoteRepo(
    private val api : GardenApi
) : BaseRepo(){

    suspend fun addGarden(
        city : String,
        latitudes : Int,
        longitudes : Int,
        age: Int,
        area: Int,
        border: List<BorderItem>?,
        density: Int,
        irrigationCycle: Int,
        irrigationDuration: Int,
        irrigationVolume: Int,
        soilType: String,
        specieSet: List<SpecieSetItem>?,
        title: String
    ) = safeApiCall {
        api.addGarden(gardenReq2Json(
            city = city,
            latitudes = latitudes,
            longitudes = longitudes,
            age = age,
            area = area,
            border = border,
            density = density,
            irrigationDuration = irrigationDuration,
            irrigationVolume = irrigationVolume,
            irrigationCycle = irrigationCycle,
            soilType = soilType,
            specieSet = specieSet,
            title = title
        ).toRequestBody("application/json".toMediaTypeOrNull()))
    }
}