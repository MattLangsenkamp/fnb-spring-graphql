package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Location
import org.springframework.stereotype.Component

@Component
class DeleteLocationMutation: Mutation {
    fun deleteLocation(id: String): Location {
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