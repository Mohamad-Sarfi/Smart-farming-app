package com.example.smartfarming.data.network.resources.garden_resource.request

data class Address(val plainAddress: String = "not set",
                   val country: String = "Iran",
                   val city: String = "",
                   val longitudes: Int = 0,
                   val id: Int = 0,
                   val state: String = "",
                   val latitudes: Int = 0)