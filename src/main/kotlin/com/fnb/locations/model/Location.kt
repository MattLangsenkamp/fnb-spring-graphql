package com.fnb.locations.model


import org.springframework.data.annotation.Id
import java.time.Instant
import org.springframework.data.relational.core.mapping.Table


@Table
data class Location(
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
        val creationDateTime: String = Instant.now().toString(),
        var locationTags: List<LocationTag>? = null
)

