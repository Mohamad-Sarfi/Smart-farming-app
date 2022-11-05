package com.example.smartfarming.utils

import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.enums.BudgetCurrencyEnum
import com.example.smartfarming.data.room.entities.enums.GardenAreaUnitEnum
import com.example.smartfarming.data.room.entities.enums.SoilTypeEnum
import com.example.smartfarming.data.room.entities.garden.CoordinateDto
import com.example.smartfarming.data.room.entities.garden.GardenAddress

val MONTH_LIST = listOf("فرور", "اردی", "خرداد", "تیر","مرداد","شهر","مهر","آبان","آذر","دی","بهمن","اسفند", )
val ACTIVITY_LIST = listOf("آبیاری","سم پاشی","تغذیه","سایر",)
val HARVEST_TYPE = listOf("تازه", "خشک", "سایر")

val initialGarden = Garden(
    0, GardenAddress(city = "Tehran", country = "Iran", id = 0, latitude = 0.0, longitude = 0.0, plainAddress = "", state = "Tehran"),
    age = 0,
    area = 0,
    areaUnit = GardenAreaUnitEnum.HECTARE.name,
    border = listOf(),
    budget = 0,
    budgetCurrency = BudgetCurrencyEnum.IRR.name,
    density = 0,
    irrigationCycle = 0,
    irrigationDuration = 0,
    irrigationVolume = 0.0,
    location = CoordinateDto(0, 0.0, 0.0),
    soilType = SoilTypeEnum.MIDDLE.name,
    specieSet = listOf(),
    title = "")

