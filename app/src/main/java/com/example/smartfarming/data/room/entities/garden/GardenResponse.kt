package com.example.smartfarming.data.room.entities.garden

import com.example.smartfarming.data.room.entities.Garden
import retrofit2.http.Body

data class GardenResponse(
    val body: Garden,
    val statusCode : Int,
    val statusCodeValue : String
)
