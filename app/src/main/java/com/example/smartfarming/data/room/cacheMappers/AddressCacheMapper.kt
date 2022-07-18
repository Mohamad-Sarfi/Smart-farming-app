package com.example.smartfarming.data.room.cacheMappers

import com.example.smartfarming.data.network.resources.user.Address
import com.example.smartfarming.data.room.entities.AddressEntity
import com.example.smartfarming.utlils.EntityMapper

class AddressCacheMapper():EntityMapper.EntityMapper<AddressEntity , Address> {
    override fun mapFromEntity(entity: AddressEntity): Address  =
        Address(
            plainAddress = entity.plainAddress,
            country = entity.country,
            city = entity.city,
            longitudes = entity.longitudes,
            id = entity.id,
            state = entity.state,
            latitudes = entity.latitudes
        )


    override fun mapToEntity(domainModel: Address): AddressEntity =
        AddressEntity(
            plainAddress = domainModel.plainAddress,
            country = domainModel.country,
            city = domainModel.city,
            longitudes = domainModel.longitudes,
            id = domainModel.id,
            state = domainModel.state,
            latitudes = domainModel.latitudes,
            row = 1
        )
}