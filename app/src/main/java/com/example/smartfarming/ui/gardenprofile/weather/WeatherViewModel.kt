package com.example.smartfarming.ui.gardenprofile.weather

import android.util.Log
import androidx.lifecycle.*
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.repositories.weather.WeatherRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class WeatherViewModel(
    private val weatherRepo: WeatherRepo,
    private val gardenRepo: GardenRepo
) : ViewModel() {


    private var _garden = MutableLiveData<Garden>()
    val garden : LiveData<Garden> = _garden

    fun getGarden(gardenName : String){
        viewModelScope.launch {
            _garden.value = gardenRepo.getGardenByName(gardenName)
        }
    }

    var weatherResponse = MutableLiveData<WeatherResponse>().apply { value= null }
    var weatherResponse1 : Resource<WeatherResponse>? = null

    fun getWeather(
        lat : String,
        long: String
    ) {
       viewModelScope.launch {
           val response = weatherRepo.getWeather(lat, long)
           if (response is Resource.Success){
               weatherResponse.value = response.value
           } else {
               Log.i("Weather", "Weather Error :(")
           }
       }
    }

    fun timeConverter(unixTime : Int) : String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = java.util.Date(unixTime.toLong() * 1000)
        return sdf.format(date)
    }

    private fun getDayOfWeek() : Int {
        return Calendar.DAY_OF_WEEK
    }

    fun getPersianDayOfWeek() : String {
        return when (getDayOfWeek()){
            1 -> "یکشنبه"
            2 -> "دوشنبه"
            3 -> "سه شنبه"
            4 -> "چهارشنبه"
            5 -> "پنجشنبه"
            6 -> "جمعه"
            7 -> "شنبه"
            else -> ""
        }
    }

}

class WeatherViewModelFactory(val weatherRepo: WeatherRepo, val gardenRepo: GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherRepo, gardenRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}