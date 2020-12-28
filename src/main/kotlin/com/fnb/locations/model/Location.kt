package com.fnb.locations.model


import com.expediagroup.graphql.annotations.GraphQLIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
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
) {
    @Transient
    lateinit var locationTags: List<LocationTag>

    @GraphQLIgnore
    fun setTags(tags: List<LocationTag>) {
        locationTags = tags
    }

    fun locationTags(): List<LocationTag> = locationTags
}

