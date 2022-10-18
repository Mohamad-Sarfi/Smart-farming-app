package com.example.smartfarming.ui.introslider

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroSliderViewModel @Inject constructor() : ViewModel() {
    var step = mutableStateOf(0)

    fun increaseStep(){
        step.value++
    }

    fun setSharedPreference(userPreferences: UserPreferences){
        viewModelScope.launch {
            userPreferences.setFirstTime("shown")
        }
    }

}