package com.example.smartfarming.data.network.resources.signup

data class ShipmentAddress(
    val city: String,
    val country: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val plainAddress: String,
    val state: String
)