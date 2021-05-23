package com.fnb.locations.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fnb.locations.service.LocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*


@Table
data class OrgUserData @Autowired(required = false) constructor(
        @Id
        val id: Int? = null,
        val orgUserId: Int,
        val username: String,
        val contact: String,
        val description: String
) {
    @Transient
    lateinit var locations: List<Location>

    @GraphQLIgnore
    fun setLocs(locs: List<Location>) {
        locations = locs
    }

    fun locations(): List<Location> {
        return locations
    }

    @Transient
    lateinit var imageUrls: List<ImageUrl>

    @GraphQLIgnore
    fun setUrls(urls: List<ImageUrl>) {
        imageUrls = urls
    }

    fun imageUrls(): List<ImageUrl> = imageUrls
}