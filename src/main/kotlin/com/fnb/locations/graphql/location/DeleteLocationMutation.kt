package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Location
import com.fnb.locations.service.LocationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeleteLocationMutation
@Autowired constructor(private val locationService: LocationService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun deleteLocation(id: UUID): Location {
        logger.debug("Request to delete location with ID $id")

        return locationService.deleteLocation(id)
    }
}