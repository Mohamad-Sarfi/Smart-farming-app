package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val activityType : String,
    val description: String,
    val executionTime : Int,
    val expireDuration : String,
    val gardenIds : List<Int>,
    @ColumnInfo(name = "title") val name : String,
    val notifyFarmer : Boolean,
    val priority : String,
    val status : String,
    val taskType : String,
    val userId: Int,
//    val recommendations : String
    )