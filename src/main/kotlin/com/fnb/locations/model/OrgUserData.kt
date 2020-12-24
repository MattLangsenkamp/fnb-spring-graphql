package com.fnb.locations.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.fnb.locations.service.LocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*


@Table
class OrgUserData @Autowired(required = false) constructor(
        @Id
        val id: Int? = null,
        val orgUserId: Int,
        val username: String,
        val contact: String,
        val description: String,
        @Column("picture_uri")
        val pictureURI: String,
        @Transient
        @GraphQLIgnore
        var locations: List<Location>?
) {
    @Autowired
    private lateinit var locationService: LocationService

    suspend fun locations(): List<Location> {
        if (locations != null) return locations!!
        return locationService.getLocationsByUser(id
                ?: throw IllegalArgumentException("user must exist to get locations"))
    }

    suspend fun copy(id: Int? = null,
                     orgUserId: Int? = null,
                     username: String? = null,
                     contact: String? = null,
                     description: String? = null,
                     pictureURI: String? = null,
                     locations: List<Location>? = null): OrgUserData {
        return OrgUserData(
                id = id ?: this.id,
                orgUserId = orgUserId ?: this.orgUserId,
                username = username ?: this.username,
                contact = contact ?: this.contact,
                description = description ?: this.description,
                pictureURI = pictureURI ?: this.pictureURI,
                locations = locations ?: this.locations
        )

    }
}