package com.fnb.locations.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fnb.locations.customExceptions.IllegalArgumentException
import com.fnb.locations.service.impl.LocationTagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import java.time.Instant
import org.springframework.data.relational.core.mapping.Table


@Table
@Scope("prototype")
data class Location @Autowired(required = false) constructor(
        @Id
        val id: Int? = null,
        val locationName: String,
        val friendlyName: String,
        val description: String,
        val latitude: Double,
        val longitude: Double,
        val pictureURI: String,
        val locationOwner: Int,
        val needsCleaning: Boolean,
        val creationDateTime: String = Instant.now().toString()
) {

    @Autowired
    private lateinit var locationTagService: LocationTagService

    suspend fun locationTags(): List<LocationTag> {
        return locationTagService.getTagsByLocation(locationId = id
                ?: throw IllegalArgumentException("location must exist to get tags"))
    }

    fun copy(id: Int? = null,
             locationName: String? = null,
             friendlyName: String? = null,
             description: String? = null,
             latitude: Double? = null,
             longitude: Double? = null,
             pictureURI: String? = null,
             locationOwner: Int? = null,
             needsCleaning: Boolean? = null,
             creationDateTime: String? = null): Location {

        return Location(
                id = id ?: this.id,
                locationName = locationName ?: this.locationName,
                friendlyName = friendlyName ?: this.friendlyName,
                description = description ?: this.description,
                latitude = latitude ?: this.latitude,
                longitude = longitude ?: this.longitude,
                pictureURI = pictureURI ?: this.pictureURI,
                locationOwner = locationOwner ?: this.locationOwner,
                needsCleaning = needsCleaning ?: this.needsCleaning,
                creationDateTime = creationDateTime ?: this.creationDateTime
        )
    }
}

