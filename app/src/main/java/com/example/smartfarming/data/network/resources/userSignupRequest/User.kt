package com.example.smartfarming.data.network.resources.userSignupRequest

data class User(
    val shipmentAddress: ShipmentAddress,
    val password: String = "",
    val address: Address,
    val phoneNumber: String = "",
    val bio: String = "",
    val fullName: String = "",
    val language: Language,
    val email: String = "")