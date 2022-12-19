package com.example.smartfarming.data.repositories.weather

import com.example.smartfarming.data.network.api.WeatherApi
import com.example.smartfarming.data.repositories.BaseRepo

class WeatherRepo(
    private val weatherApi: WeatherApi
) : BaseRepo() {
    val apiKey = "b8bff1a924e0ab2766e553ba49b42838"

    suspend fun getWeather(
        lat : String,
        long : String
    ) = safeApiCall {
        weatherApi.getWeather(lat = lat, long =  long, apiKey =  apiKey)
    }

}