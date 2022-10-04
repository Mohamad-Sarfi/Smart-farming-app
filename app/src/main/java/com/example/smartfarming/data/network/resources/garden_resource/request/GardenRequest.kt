package com.example.smartfarming.data.network.resources.garden_resource.request

import com.google.gson.Gson

data class GardenRequest(val area: Int = 0,
                         val areaUnit: String = "square meter",
                         val border: List<BorderItem>?,
                         val address: Address,
                         val density: Int = 0,
                         val budgetCurrency: String = "IRR",
                         val irrigationCycleUnit: String = "day",
                         val irrigationCycle: Int = 0,
                         val irrigationVolumeUnit: String = "liter",
                         val irrigationVolume: Float = 0f,
                         val title: String = "",
                         val irrigationDurationUnit: String = "hours",
                         val specieSet: List<SpecieSetItem>?,
                         val irrigationDuration: Int = 0,
                         val soilType: String = "",
                         val location: Location,
                         val id: Int = 0,
                         val age: Int = 0,
                         val budget: Int = 0
)

fun gardenReq2Json(
    city : String,
    latitudes : Double,
    longitudes : Double,
    age: Int,
    area: Int,
    border: List<BorderItem>?,
    density: Int,
    irrigationCycle: Int,
    irrigationDuration: Int,
    irrigationVolume: Float,
    soilType: String,
    specieSet: List<SpecieSetItem>?,
    title: String
) : String {
    val gson = Gson()

    return gson.toJson(GardenRequest(
                area = area,
                border = border,
                address = Address(city = city),
                density = density,
                irrigationCycle = irrigationCycle,
                irrigationVolume = irrigationVolume,
                title = title,
                specieSet = specieSet,
                irrigationDuration = irrigationDuration,
                soilType = soilType,
                location = Location(latitude = latitudes, longitude = longitudes),
                age = age
        )
    )
}
