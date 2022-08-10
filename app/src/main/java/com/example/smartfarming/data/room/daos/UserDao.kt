package com.example.smartfarming.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.data.room.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressEntity: AddressEntity)

    @Query("SELECT * FROM user")
    suspend fun getUserInfo(): UserEntity

    @Query("SELECT * FROM user_address")
    suspend fun getAddressInfo(): AddressEntity

}