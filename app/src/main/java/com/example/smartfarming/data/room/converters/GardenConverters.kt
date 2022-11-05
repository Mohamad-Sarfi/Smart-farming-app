package com.example.smartfarming.data.room.converters

import androidx.room.TypeConverter
import com.example.smartfarming.data.room.entities.garden.GardenAddress
import com.google.gson.Gson

class GardenConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromGardenAddress(value : GardenAddress) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGardenAddress(value: String) : GardenAddress {
        return gson.fromJson(value, GardenAddress::class.java)
    }


}