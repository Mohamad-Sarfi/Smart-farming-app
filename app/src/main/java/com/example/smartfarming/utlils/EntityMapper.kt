package com.example.smartfarming.utlils

class EntityMapper {
    interface EntityMapper <Entity, DomainModel>{

        fun mapFromEntity(entity: Entity): DomainModel

        fun mapToEntity(domainModel: DomainModel): Entity
    }
}