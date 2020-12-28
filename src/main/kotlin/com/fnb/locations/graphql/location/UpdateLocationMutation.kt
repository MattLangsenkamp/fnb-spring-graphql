package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.LocationService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateLocationMutation
@Autowired constructor(private val locationService: LocationService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun updateLocation(graphQLContext: MyGraphQLContext,
                               id: Int,
                               name: String?,
                               friendlyName: String?,
                               description: String?,
                               latitude: Double?,
                               longitude: Double?,
                               picture: String?,
                               typeTags: List<LocationTag>?): Location {
        logger.debug("request to update location with id: $id received")

        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add new location")
        return locationService.updateLocation(
                loggedInUser = loggedInUser,
                id = id,
                name = name,
                friendlyName = friendlyName,
                description = description,
                latitude = latitude,
                longitude = longitude,
                picture = picture,
                newLocationTags = typeTags)
    }
}