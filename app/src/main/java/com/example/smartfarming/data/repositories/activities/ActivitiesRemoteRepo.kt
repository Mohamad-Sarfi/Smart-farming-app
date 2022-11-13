package com.example.smartfarming.data.repositories.activities

import android.util.Log
import com.example.smartfarming.data.network.api.ActivitiesApi
import com.example.smartfarming.data.repositories.BaseRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class ActivitiesRemoteRepo(
    private val api: ActivitiesApi
) : BaseRepo() {

    suspend fun addFertilization(
        auth : String,
        fertilizationEntity: FertilizationEntity
    ) = safeApiCall {
        Log.i("TAG auth", "$auth")
        api.addFertilization(
            "Bearer $auth",
            request = ToJason.fertilization2Json(fertilizationEntity).toRequestBody("application/json".toMediaTypeOrNull())
        )
    }
}