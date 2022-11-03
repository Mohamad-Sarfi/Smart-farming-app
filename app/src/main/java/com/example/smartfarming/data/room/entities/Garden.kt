package com.example.smartfarming.data.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.smartfarming.data.room.entities.enums.BudgetCurrencyEnum
import com.example.smartfarming.data.room.entities.enums.GardenAreaUnitEnum
import com.example.smartfarming.data.room.entities.garden.CoordinateDto
import com.example.smartfarming.data.room.entities.garden.GardenAddress
import com.example.smartfarming.data.room.entities.garden.SpecieDto

@Entity(tableName = "garden_table")
data class Garden(
    @PrimaryKey(autoGenerate = true) @ColumnInfo
        (name = "id") val id : Int,
    @ColumnInfo(name = "address")
        val address: GardenAddress,
    @ColumnInfo(name = "age")
        val age: Int,
    @ColumnInfo(name = "area")
        val area : Int,
    @ColumnInfo(name = "areaUnit")
        val areaUnit : String,
    @ColumnInfo(name = "border")
        val border : List<CoordinateDto>?,
    @ColumnInfo(name = "budget")
        val budget : Int,
    @ColumnInfo(name = "budgetCurrency")
        val budgetCurrency : String = BudgetCurrencyEnum.IRR.name,
    @ColumnInfo(name = "density")
        val density : Int,
    @ColumnInfo(name = "irrigationCycle")
        val irrigationCycle : Int,
    @ColumnInfo(name = "irrigationCycleUnit")
        val irrigationCycleUnit : String = GardenAreaUnitEnum.DAY.name,
    @ColumnInfo(name = "irrigationDuration")
        val irrigationDuration : Int,
    @ColumnInfo(name = "irrigationDurationUnit")
        val irrigationDurationUnit : String = GardenAreaUnitEnum.HOUR.name,
    @ColumnInfo(name = "irrigationVolume")
        val irrigationVolume : Double,
    @ColumnInfo(name = "irrigationVolumeUnit")
        val irrigationVolumeUnit : String = GardenAreaUnitEnum.LITER.name,
    @ColumnInfo(name = "location")
        val location : CoordinateDto,
    @ColumnInfo(name = "soilType")
        val soilType : String,
    @ColumnInfo(name = "specieSet")
        val specieSet : List<SpecieDto>,
    @NonNull @ColumnInfo
        (name = "title") val title: String,
//    @ColumnInfo (name = "irrigation_type")
//        val irrigation_type : String = "",
//    @ColumnInfo(name = "user_id")
//        val user_id: Int
)

