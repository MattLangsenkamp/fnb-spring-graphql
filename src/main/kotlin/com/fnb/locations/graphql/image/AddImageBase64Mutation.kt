package com.fnb.locations.graphql.image

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.ImageUrl
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.ImageService
import com.fnb.locations.service.impl.LocationService
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddImageBase64Mutation @Autowired constructor(private val imageService: ImageService,
                                                    private val locationService: LocationService,
                                                    private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun addImageBase64(
            graphQLContext: MyGraphQLContext,
            imageBase64: String,
            type: String,
            ownerId: Int): ImageUrl {

        logger.info("request to update user data with id ")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to update user")


        val resourceOwner = when (type) {
            "location" -> locationService.getLocation(ownerId)
            "orgUser" -> userDataService.getUserDataByOrgUser(ownerId)
            else -> throw IllegalArgumentException("Images not supported for that resource type")
        }

        return imageService.uploadImageBase64(
                loggedInUser = loggedInUser,
                img = imageBase64,
                type = type,
                ownerId = ownerId,
                resourceOwner = resourceOwner
        )
    }

}