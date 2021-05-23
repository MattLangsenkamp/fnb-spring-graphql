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
class DeleteImageMutation
@Autowired constructor(private val imageService: ImageService,
                       private val locationService: LocationService,
                       private val userDataService: UserDataService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun deleteImage(
            graphQLContext: MyGraphQLContext,
            imageId: Int,
            type: String
    ): ImageUrl {

        logger.info("request to delete image with id $imageId")

        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to update user")

        val imgToDel = imageService.getImage(imageId)

        val resourceOwner = when (type) {
            "location" -> locationService.getLocation(imgToDel.ownerId)
            "orgUser" -> userDataService.getUserDataByOrgUser(imgToDel.ownerId)
            else -> throw IllegalArgumentException("Images not supported for that resource type")
        }

        return imageService.deleteImage(loggedInUser = loggedInUser, id = imageId, type = type, resourceOwner)

    }
}