package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.service.LocationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateLocationMutation
@Autowired constructor(private val locationService: LocationService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun updateLocation(id: Int,
                               name: String,
                               friendlyName: String,
                               description: String,
                               latitude: Double,
                               longitude: Double,
                               picture: String,
                               typeTags: List<LocationTag>): Location {
        return locationService.updateLocation(
                id = id,
                name = name,
                friendlyName = friendlyName,
                description = description,
                latitude = latitude,
                longitude = longitude,
                picture = picture,
                typeTags = typeTags)
    }
}