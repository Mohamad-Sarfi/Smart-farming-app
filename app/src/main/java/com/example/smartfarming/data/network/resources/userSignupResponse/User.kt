package com.example.smartfarming.data.network.resources.userSignupResponse

data class User(val shipmentAddress: ShipmentAddress,
                val address: Address,
                val phoneNumber: String = "",
                val authority: Authority,
                val bio: String = "",
                val fullName: String = "",
                val language: Language,
                val confirmedPhone: Boolean = false,
                val id: Int = 0,
                val userType: String = "",
                val email: String = "",
                val enabled: Boolean = false)