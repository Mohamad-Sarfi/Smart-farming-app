package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fertilization_table")
data class FertilizationEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "title") val name : String,
    @ColumnInfo(name = "type") val fertilization_type : String,
    val time : String,
    val gardenId : Int,
    val usageType : String,
    val volumePerUnit : Int,
    val volumeUnit : String
)