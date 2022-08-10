package com.example.smartfarming.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.user.Address
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.network.resources.user.User
import com.example.smartfarming.data.repositories.garden.GardenRemoteRepo
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@HiltViewModel
class ProfileViewModel(
    private val repository: GardenRemoteRepo
) : ViewModel() {
     val user = MutableLiveData<UserEntity>()
     val address = MutableLiveData<AddressEntity>()
    val fullName = MutableLiveData<String>().apply {
        value = ""
    }
    val phoneNumber = MutableLiveData<String>().apply {
        value = ""
    }
    val bio = MutableLiveData<String>().apply {
        value = ""
    }
    val cityName = MutableLiveData<String>().apply {
        value = ""
    }
    val stateName = MutableLiveData<String>().apply {
        value = ""
    }
    private var city = ""
    private var state = ""

    fun setFullName(name: String) {
        fullName.value = name
    }

    fun setPhone(userPhone: String) {
        phoneNumber.value = userPhone
    }

    fun setState(stateName: String) {
        state = stateName
    }

    fun setCity(cityName: String) {
        city = cityName
    }

    fun setBio(bio: String) {
        this.bio.value = bio
    }

    val editProfileResponse = MutableLiveData<Resource<LoginResponse>>()
    fun editProfile() {
        viewModelScope.launch {
            editProfileResponse.value = repository.editProfile(
                fullName = fullName.value!!,
                phoneNumber = phoneNumber.value!!,
                state = state,
                city = city,
                bio = bio.value!!
            )
        }
    }
    fun getUserInformation(){
        viewModelScope.launch{
            user.apply {
                 repository.getUserInfo().onEach {
                    value = it
                }.launchIn(viewModelScope)
            }
            address.apply {
                 repository.getAddress().onEach {
                     value = it
                 }.launchIn(viewModelScope)
            }
//            user.value = repository.getUserInfo()
//            address.value = repository.getAddress()
        }
    }

    class ProfileViewModelFactory(
        private val repository: GardenRemoteRepo
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown viewModel class")
        }
    }

}