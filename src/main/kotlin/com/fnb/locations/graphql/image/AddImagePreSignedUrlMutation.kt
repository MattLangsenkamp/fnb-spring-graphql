package com.fnb.locations.graphql.image

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.customExceptions.IllegalArgumentException

import com.fnb.locations.model.ImageUrl
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.ImageService
import com.fnb.locations.service.impl.LocationService
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddImagePreSignedUrlMutation
@Autowired constructor(private val imageService: ImageService,
                       private val locationService: LocationService,
                       private val userDataService: UserDataService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun addImagePresignedUrl(
            graphQLContext: MyGraphQLContext,
            mimeType: String,
            type: String,
            ownerId: Int): ImageUrl {

        logger.info("request to add image ")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add image")

        val resourceOwner = when (type) {
            "location" -> locationService.getLocation(ownerId)
            "orgUser" -> userDataService.getUserDataByOrgUser(ownerId)
            else -> throw IllegalArgumentException("no")
        }

        return imageService.uploadImagePreSignedUrl(
                loggedInUser = loggedInUser,
                mimeType = mimeType,
                type = type,
                ownerId = ownerId,
                resourceOwner = resourceOwner
        )
    }
}