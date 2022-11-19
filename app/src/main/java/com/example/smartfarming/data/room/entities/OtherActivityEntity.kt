package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "other_activities_table")
data class OtherActivityEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "title") val name : String,
    val time : String,
    val description : String,
    val cause : String,
    val gardenId : Int
)
