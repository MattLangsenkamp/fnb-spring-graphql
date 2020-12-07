package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.Location
import com.fnb.locations.service.LocationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocationQuery
@Autowired constructor(private val locationService: LocationService) : Query {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun location(id: UUID): Location {
        logger.debug("Request to get location with id $id")
        // poo
        return locationService.getLocation(id)

    }
}