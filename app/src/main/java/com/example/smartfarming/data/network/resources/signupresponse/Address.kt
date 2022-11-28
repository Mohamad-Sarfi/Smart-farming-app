package com.example.smartfarming.data.network.resources.signupresponse

data class Address(
    val city: String,
    val country: String,
    val id: Int,
    val latitude: Int,
    val longitude: Int,
    val plainAddress: String,
    val state: String
)