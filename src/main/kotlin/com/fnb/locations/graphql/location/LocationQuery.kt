package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.Location
import com.fnb.locations.service.impl.LocationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocationQuery
@Autowired constructor(private val locationService: LocationService) : Query {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun location(id: Int): Location {
        logger.debug("Request to get location with id $id")
        return locationService.getLocation(id)

    }
}