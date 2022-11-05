package com.example.smartfarming.data.room.entities.garden

import retrofit2.http.Body

data class GardenResponse(
    val body: Body,
    val statusCode : Int,
    val statusCodeValue : String
)
