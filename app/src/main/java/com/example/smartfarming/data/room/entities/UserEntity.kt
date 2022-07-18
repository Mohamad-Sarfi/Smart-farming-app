package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    val row: Long =1 ,
    @ColumnInfo(name = "address")
    val address: String="",
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String = "",
    @ColumnInfo(name = "full_name")
    val fullName: String = "",
    @ColumnInfo(name = "bio")
    val bio: String = "",
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "confirmed_phone")
    val confirmedPhone: Boolean = false,
    @ColumnInfo(name = "user_type")
    val userType: String = "",
    @ColumnInfo(name = "enabled")
    val enabled: Boolean = false,
    @ColumnInfo(name = "email")
    val email: String = ""
)