package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pesticide_table")
data class PesticideEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val name : String,
    val garden_name : String,
    val pest : String,
    val date : String,
    val pesticide_volume : Float
)
