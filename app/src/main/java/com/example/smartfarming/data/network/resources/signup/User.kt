package com.example.smartfarming.data.network.resources.signup

data class User(
    val address: Address,
    val bio: String,
    val email: String,
    val fullName: String,
    val language: Language,
    val password: String,
    val phoneNumber: String,
    val shipmentAddress: ShipmentAddress
)