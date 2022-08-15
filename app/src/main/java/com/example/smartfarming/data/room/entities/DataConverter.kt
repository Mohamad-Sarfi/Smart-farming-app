package com.example.smartfarming.data.room.entities

import androidx.room.TypeConverter
import com.google.android.libraries.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LatLongListConverter {
    val gson = Gson()

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


}