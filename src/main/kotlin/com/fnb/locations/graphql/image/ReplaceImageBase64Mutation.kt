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
class ReplaceImageBase64Mutation
@Autowired constructor(private val imageService: ImageService,
                       private val locationService: LocationService,
                       private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun replaceImageBase64(
            graphQLContext: MyGraphQLContext,
            img: String,
            id: Int,
            type: String
    ): ImageUrl {

        logger.info("request to replace image with id $id")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to update user")

        val imgToDel = imageService.getImage(id)

        val resourceOwner = when (type) {
            "location" -> locationService.getLocation(imgToDel.ownerId)
            "orgUser" -> userDataService.getUserDataByOrgUser(imgToDel.ownerId)
            else -> throw IllegalArgumentException("Images not supported for that resource type")
        }

        return imageService.replaceImageBase64(
                loggedInUser = loggedInUser,
                img = img,
                id = id,
                type = type,
                resourceOwner = resourceOwner)
    }
}