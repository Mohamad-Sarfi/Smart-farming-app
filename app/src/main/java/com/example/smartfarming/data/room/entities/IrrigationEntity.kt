package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "irrigation_table")
data class IrrigationEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val date : String,
    val garden_name : String,
    val irrigation_volume : Double,
    val irrigation_duration : Int,
    val irrigation_type : String
)
