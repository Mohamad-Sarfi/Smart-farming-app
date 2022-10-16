package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "other_activities_table")
data class OtherActivityEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val name : String,
    val date : String,
    val description : String,
    val cause : String,
    val garden_name : String
)
