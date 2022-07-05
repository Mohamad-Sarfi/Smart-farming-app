package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartfarming.data.network.resources.garden_resource.request.PlantType

@Entity(tableName = "harvest_table")
data class Harvest(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val weight : Float,
    val type : String,
    val year : String,
    val month : String,
    val day : String,
    val gardenName : String,
    val plantType: String = "پسته"
)