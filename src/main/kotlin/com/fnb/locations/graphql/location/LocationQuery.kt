package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.Location
import org.springframework.stereotype.Component

@Component
class LocationQuery: Query {
    fun location(id: String): Location {
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