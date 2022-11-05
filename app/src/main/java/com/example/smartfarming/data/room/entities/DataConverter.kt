package com.example.smartfarming.data.room.entities

import androidx.room.TypeConverter
import com.example.smartfarming.data.room.entities.garden.CoordinateDto
import com.example.smartfarming.data.room.entities.garden.GardenAddress
import com.example.smartfarming.data.room.entities.garden.SpecieDto
import com.google.android.libraries.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GardenDbConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromLatLongList(latLongList : List<LatLng>) : String? {
        if (latLongList.isNullOrEmpty()){
            return null
        }
        return gson.toJson(latLongList)
    }

    @TypeConverter
    fun fromLatLongString(data : String) : List<LatLng> {
        val listType = object : TypeToken<List<LatLng>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromGardenAddress(value : GardenAddress) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGardenAddress(value: String) : GardenAddress {
        return gson.fromJson(value, GardenAddress::class.java)
    }

    @TypeConverter
    fun fromBorderList(value: List<CoordinateDto>) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBorderList(value: String) : List<CoordinateDto> {
        return gson.fromJson(value, Array<CoordinateDto>::class.java).toList() as ArrayList<CoordinateDto>
    }

    @TypeConverter
    fun toCoordinateDto(value: String) : CoordinateDto {
        return gson.fromJson(value, CoordinateDto::class.java)
    }

    @TypeConverter
    fun fromCoordinateDto(value: CoordinateDto) : String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSpecieSet(value: String) : List<SpecieDto> {
        return gson.fromJson(value, Array<SpecieDto>::class.java).toList()
    }

    @TypeConverter
    fun fromSpecieSet(value: List<SpecieDto>) : String {
        return gson.toJson(value)
    }
}