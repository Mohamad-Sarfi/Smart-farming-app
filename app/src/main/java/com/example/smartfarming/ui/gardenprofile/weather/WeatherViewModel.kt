package com.example.smartfarming.ui.gardenprofile.weather

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.repositories.weather.WeatherRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.utils.PersianCalender
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class WeatherViewModel(
    private val weatherRepo: WeatherRepo,
    private val gardenRepo: GardenRepo
) : ViewModel() {


    private var _garden = MutableLiveData<Garden>()
    val garden : LiveData<Garden> = _garden

    val selectedDay = mutableStateOf(0)

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

    fun getDayOfWeek() : Int {
        return Calendar.DAY_OF_WEEK
    }

    fun getPersianDayOfWeek(day : Int = getDayOfWeek()) : String {
        return when (day){
            7 -> "یکشنبه"
            1 -> "دوشنبه"
            2 -> "سه شنبه"
            3 -> "چهارشنبه"
            4 -> "پنجشنبه"
            5 -> "جمعه"
            6 -> "شنبه"
            else -> ""
        }
    }

    fun getPersianDayOfWeekWeather(index : Int) : Int {
        /*
        returns respective week days for each row item in weather screen
         */
        val today = getDayOfWeek()
        if (today + index <= 7){
            return today + index
        } else {
            return 7 - today + index
        }

    }

   fun getDayOfMonth(index : Int) : Int{
       var day = PersianCalender.getShamsiDateMap()["day"]!!
       val month = PersianCalender.getShamsiDateMap()["month"]!!
       day += index
       if (month < 7){
           if (day <= 31){
               return day
           } else {
               return day - 31
           }
       } else {
           if (day < 31){
               return day
           } else {
               return day - 30
           }
       }

   }

    fun getPersianWeatherDescription(englishDescription : String) : String {
        return when(englishDescription){
            "clear sky" -> "آسمان صاف"
            "few clouds" -> "لکه های ابر"
            "scattered clouds" -> "کمی ابری"
            "broken clouds" -> "ابری"
            "shower rain" -> "رگبار"
            "rain" -> "بارانی"
            "thunderstorm" -> "رعد و برق"
            "snow" -> "برفی"
            "mist" -> "مه آلود"
            else -> "آسمان صاف"
        }
    }

}

class WeatherViewModelFactory(val weatherRepo: WeatherRepo, val gardenRepo: GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherRepo, gardenRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}