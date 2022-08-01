package com.example.smartfarming.utils

import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task

fun getTaskList(gardensList : List<Garden>) : List<Task>{

return listOf<Task>(
        Task(0,
                "ولک پاشی",
                activity_type = ActivityTypesEnum.FERTILIZATION.name,
                description = "به دلیل عدم تامین نیاز سرمایی",
                start_date = "",
                finish_date = "",
                garden_name =  gardensList?.get(0)?.name!!,
                recommendations = "روغن ولک",
                user_id = 5,
                seen = false
        ),
        Task(0,
            "سم پاشی",
            activity_type = ActivityTypesEnum.PESTICIDE.name,
            description = "مبارزه با پسیل",
            start_date = "",
            finish_date = "",
            garden_name = gardensList?.get(0)?.name!!,
            recommendations = "روغن ولک",
            user_id = 5,
            seen = false
        )
        ,
        Task(0,
            "آبیاری اسفند",
            activity_type = ActivityTypesEnum.IRRIGATION.name,
            description = "موعد آبیاری اسفند",
            start_date = "",
            finish_date = "",
            garden_name = gardensList?.get(0)?.name!!,
            recommendations = "",
            user_id = 5,
            seen = false
        )
        ,
        Task(0,
            "کود دامی",
            activity_type = ActivityTypesEnum.FERTILIZATION.name,
            description = "با توجه به ماده عالی خاک نیاز به تامین کود دامی",
            start_date = "",
            finish_date = "",
            garden_name = gardensList?.get(0)?.name!!,
            recommendations = "کود گاو",
            user_id = 5,
            seen = false
        )
    )
}
