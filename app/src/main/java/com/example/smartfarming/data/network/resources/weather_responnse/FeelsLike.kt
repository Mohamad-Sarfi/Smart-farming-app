package com.example.smartfarming.data.network.resources.weather_responnse

data class FeelsLike(val eve: Double = 0.0,
                     val night: Double = 0.0,
                     val day: Double = 0.0,
                     val morn: Double = 0.0)