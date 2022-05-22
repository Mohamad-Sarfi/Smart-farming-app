package com.example.smartfarming.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true) @ColumnInfo (name = "rowid") val id : Int,
    @NotNull @ColumnInfo
        (name = "title") val title : String,
    @ColumnInfo
        (name = "text") val text : String,
    @ColumnInfo
        (name = "text") val pic : String,
    @ColumnInfo
        (name = "source") val source : String,
    @ColumnInfo
        (name = "author") val author : String,
    @ColumnInfo
        (name = "references") val references : List<String>,
    @ColumnInfo
        (name = "tags") val tags : List<String>
)