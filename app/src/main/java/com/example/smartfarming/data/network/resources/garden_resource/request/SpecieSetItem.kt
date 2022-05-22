package com.example.smartfarming.data.network.resources.garden_resource.request

data class SpecieSetItem(val description: String = "",
                         val id: Int = 0,
                         val title: String = "",
                         val plantType: PlantType)