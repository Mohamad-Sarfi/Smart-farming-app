package com.example.smartfarming.data.network.api

import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.task.TaskPageResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {

    @POST("farmer/privileged/task")
    suspend fun addTask(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ) : Task

    @GET("farmer/privileged/task/byFarmerId/{farmerId}/{pageNumber}/{pageSize}")
    suspend fun getTaskByFarmerId(
        @Header("Authorization") authHeader : String,
        @Path(value = "farmerId") farmerId : Int,
        @Path(value = "pageNumber")  pageNumber : Int,
        @Path(value = "pageSize") pageSize : Int
    ) : List<TaskPageResponse>

    @GET("farmer/privileged/task/byGardenId/{gardenId}/{pageNumber}/{pageSize}")
    suspend fun getTaskByGardenId(
        @Header("Authorization") authHeader : String,
        @Path("gardenId") gardenId : Int,
        @Path("pageNumber") pageNumber : Int,
        @Path("pageSize") pageSize : Int
    )

}