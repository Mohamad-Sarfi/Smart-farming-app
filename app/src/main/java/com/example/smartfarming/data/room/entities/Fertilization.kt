package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fertilization_table")
data class FertilizationEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val name : String,
    val fertilization_type : String,
    val date : String,
    val volume : Float,
    val garden_name : String,
)