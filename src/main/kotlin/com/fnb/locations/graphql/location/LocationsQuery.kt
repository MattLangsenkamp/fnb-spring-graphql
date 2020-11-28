package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.Location
import org.springframework.stereotype.Component

@Component
class LocationsQuery: Query {
    fun locations(): List<Location> {
        return listOf(Location("id",
        "name",
        "friendlyLocation",
        "description",
        0.0,
        0.0,
        "pictureURI",
        "locationOwner",
        listOf("type")))
    }
}