package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.Location
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateUserDataMutation
@Autowired constructor(private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun updateUserData(
            graphQLContext: MyGraphQLContext,
            id: Int,
            username: String?,
            contact: String?,
            description: String?,
            picture: String?,
            locations: List<Location>?): OrgUserData {

        logger.info("request to update user data with id ")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add new location")
        return userDataService.updateUserData(
                loggedInUser,
                id,
                username,
                contact,
                description,
                picture,
        )
    }
}