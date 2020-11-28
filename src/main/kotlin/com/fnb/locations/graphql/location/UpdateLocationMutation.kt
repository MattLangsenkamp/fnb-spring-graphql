package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Location
import org.springframework.stereotype.Component

@Component
class UpdateLocationMutation: Mutation {
    fun updateLocation(id: String,
                       name: String,
                    friendlyName: String, description: String,
                    latitude: Double,
                    longitude: Double,
                    picture: String,
                    locationOwner: String,
                    type: String): Location {
        return Location("id",
        "name",
        "friendlyLocation",
        "description",
        0.0,
        0.0,
        "pictureURI",
        "locationOwner",
        listOf("type"))
    }
}