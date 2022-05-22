package com.example.smartfarming.data.network.resources.user

data class User(val shipmentAddress: ShipmentAddress,
                val phoneNumber: String = "",
                val address: Address,
                val authority: Authority,
                val fullName: String = "",
                val bio: String = "",
                val language: Language,
                val id: Int = 0,
                val confirmedPhone: Boolean = false,
                val userType: String = "",
                val enabled: Boolean = false,
                val email: String = "")