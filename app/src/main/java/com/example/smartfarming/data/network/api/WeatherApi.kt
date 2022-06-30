package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat : String,
        @Query("lon") long : String,
        @Query("appid") apiKey : String) : WeatherResponse
}