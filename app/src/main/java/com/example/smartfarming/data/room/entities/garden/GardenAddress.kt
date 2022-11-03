package com.example.smartfarming.data.room.entities.garden

data class GardenAddress(
    val city : String,
    val country : String,
    val id : Int,
    val latitude : Double,
    val longitude : Double,
    val plainAddress : String,
    val state : String
)
