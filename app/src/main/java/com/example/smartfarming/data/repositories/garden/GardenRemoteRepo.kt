package com.example.smartfarming.data.repositories.garden

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.api.GardenApi
import com.example.smartfarming.data.network.resources.garden_resource.request.BorderItem
import com.example.smartfarming.data.network.resources.garden_resource.request.GardenRequest
import com.example.smartfarming.data.network.resources.garden_resource.request.SpecieSetItem
import com.example.smartfarming.data.network.resources.garden_resource.request.gardenReq2Json
import com.example.smartfarming.data.network.resources.user.Address
import com.example.smartfarming.data.network.resources.user.LoginResponse
import com.example.smartfarming.data.network.resources.user.User
import com.example.smartfarming.data.network.resources.userSignupRequest.editReq2JSON
import com.example.smartfarming.data.network.resources.userSignupRequest.signUpReq2JSON
import com.example.smartfarming.data.repositories.BaseRepo
import com.example.smartfarming.data.room.cacheMappers.AddressCacheMapper
import com.example.smartfarming.data.room.cacheMappers.UserCacheMapper
import com.example.smartfarming.data.room.daos.UserDao
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.UserEntity
import com.example.smartfarming.ui.addactivities.Screens.DisplayDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GardenRemoteRepo(
    private val api : GardenApi,
    val userPreferences: UserPreferences,
    private val dao: UserDao
) : BaseRepo(){
    lateinit var token: String
    private val userCacheMapper= UserCacheMapper()
    private val addressCacheMapper= AddressCacheMapper()
    suspend fun addGarden(
        city : String,
        latitudes : Int,
        longitudes : Int,
        age: Int,
        area: Int,
        border: List<BorderItem>?,
        density: Int,
        irrigationCycle: Int,
        irrigationDuration: Int,
        irrigationVolume: Int,
        soilType: String,
        specieSet: List<SpecieSetItem>?,
        title: String
    ) = safeApiCall {
        api.addGarden(gardenReq2Json(
            city = city,
            latitudes = latitudes,
            longitudes = longitudes,
            age = age,
            area = area,
            border = border,
            density = density,
            irrigationDuration = irrigationDuration,
            irrigationVolume = irrigationVolume,
            irrigationCycle = irrigationCycle,
            soilType = soilType,
            specieSet = specieSet,
            title = title
        ).toRequestBody("application/json".toMediaTypeOrNull()))
    }

    fun getUserInfo() : Flow<UserEntity> = flow {
        val userInfo = dao.getUserInfo()
        emit(userInfo)
    }.flowOn(Dispatchers.IO)

      fun getAddress() : Flow<AddressEntity> = flow {
          val addressInfo = dao.getAddressInfo()
          emit(addressInfo)
    }.flowOn(Dispatchers.IO)

    suspend fun editProfile(
        fullName: String,
        phoneNumber: String,
        state: String,
        city: String,
        bio: String
    ) = safeApiCall {
        userPreferences.authToken.collect{ token = it!!}
        val editProfile = api.editProfile(
                token,
                editReq2JSON(
                    fullName = fullName,
                    password = "don't save password",
                    phoneNumber = phoneNumber,
                    state = state,
                    city = city,
                    bio = bio
                )
                    .toRequestBody("application/json".toMediaTypeOrNull())
            )
            dao.insertUser(userCacheMapper.mapToEntity(editProfile.response.inRole.user))
            dao.insertAddress(addressCacheMapper.mapToEntity(editProfile.response.inRole.user.address!!))
             return@safeApiCall editProfile
        }



}