package com.fnb.locations.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fnb.locations.customExceptions.IllegalArgumentException
import com.fnb.locations.service.impl.LocationTagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import java.time.Instant
import org.springframework.data.relational.core.mapping.Table


@Table
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
        @Transient
        @GraphQLIgnore
        var locationTags: List<LocationTag>?,
        val creationDateTime: String = Instant.now().toString(),
) {

    @Autowired
    private lateinit var locationTagService: LocationTagService

    suspend fun locationTags(): List<LocationTag> {
        if (locationTags != null) return locationTags as List<LocationTag>
        return locationTagService.getTagsByLocation(locationId = id
                ?: throw IllegalArgumentException("location must exist to get tags"))
    }
}

