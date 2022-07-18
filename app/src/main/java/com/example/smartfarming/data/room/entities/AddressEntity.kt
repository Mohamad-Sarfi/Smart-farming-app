package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_address")
data class AddressEntity
    (
    @PrimaryKey(autoGenerate = false)
    val row: Long = 1,
    @ColumnInfo(name = "plain_address")
    val plainAddress: String = "",
    @ColumnInfo(name = "country")
    val country: String = "",
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "longitudes")
    val longitudes: Int = 0,
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "state")
    val state: String = "",
    @ColumnInfo(name = "latitudes")
    val latitudes: Int = 0
)