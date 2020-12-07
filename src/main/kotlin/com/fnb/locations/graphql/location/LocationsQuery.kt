package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.Location
import com.fnb.locations.service.LocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocationsQuery
@Autowired constructor(private val locationService: LocationService) : Query {
    suspend fun locations(): List<Location> {
        return locationService.getAllLocations()
    }
}