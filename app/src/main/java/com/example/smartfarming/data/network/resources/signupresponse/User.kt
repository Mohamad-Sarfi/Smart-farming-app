package com.example.smartfarming.data.network.resources.signupresponse

data class User(
    val address: Address,
    val authority: Authority,
    val bio: String,
    val confirmedPhone: Boolean,
    val email: String,
    val enabled: Boolean,
    val fullName: String,
    val id: Int,
    val language: Language,
    val phoneNumber: String,
    val shipmentAddress: ShipmentAddress,
    val userType: String
)