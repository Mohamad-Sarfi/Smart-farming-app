package com.example.smartfarming.data.network.resources.weather_response

data class WeatherResponse(val current: Current,
                           val timezone: String = "",
                           val timezoneOffset: Int = 0,
                           val daily: List<DailyItem>?,
                           val lon: Double = 0.0,
                           val hourly: List<HourlyItem>?,
                           val lat: Double = 0.0)