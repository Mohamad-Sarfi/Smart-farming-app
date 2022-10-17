package com.example.smartfarming.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val garden_name : String,
    val description: String,
    val activity_type : String,
    val start_date : String,
    val finish_date : String,
    val recommendations : String,
    val user_id : Int,
    val seen : Boolean)