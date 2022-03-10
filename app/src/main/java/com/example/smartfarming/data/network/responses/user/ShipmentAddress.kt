package com.example.smartfarming.data.network.responses.user

data class ShipmentAddress(val plainAddress: String = "",
                           val country: String = "",
                           val city: String = "",
                           val longitudes: Int = 0,
                           val id: Int = 0,
                           val state: String = "",
                           val latitudes: Int = 0)