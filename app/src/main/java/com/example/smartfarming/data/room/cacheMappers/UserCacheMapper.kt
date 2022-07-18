package com.example.smartfarming.data.room.cacheMappers

import com.example.smartfarming.data.network.resources.user.User
import com.example.smartfarming.data.room.entities.UserEntity
import com.example.smartfarming.utlils.EntityMapper

class UserCacheMapper(): EntityMapper.EntityMapper<UserEntity , User>{
    override fun mapFromEntity(entity: UserEntity): User {
        return User(
            null,
            entity.phoneNumber,
            null,
            null,
            entity.fullName,
            entity.bio,
            null,
            entity.id,
            entity.confirmedPhone,
            entity.userType,
            entity.enabled,
            entity.email
        )
    }

    override fun mapToEntity(domainModel: User): UserEntity {
        return UserEntity(
            1,
            "",
            domainModel.phoneNumber,
            domainModel.fullName,
            domainModel.bio,
            domainModel.id,
            domainModel.confirmedPhone,
            domainModel.userType,
            domainModel.enabled,
            domainModel.email
        )
    }
}