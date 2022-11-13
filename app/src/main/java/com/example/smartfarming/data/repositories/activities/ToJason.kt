package com.example.smartfarming.data.repositories.activities

import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.google.gson.Gson

class ToJason {
    companion object {
        fun fertilization2Json(fertilizationEntity: FertilizationEntity) : String {
            val gson = Gson()
            return gson.toJson(fertilizationEntity)
        }
    }
}