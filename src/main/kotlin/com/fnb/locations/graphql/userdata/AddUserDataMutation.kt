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
class AddUserDataMutation
@Autowired constructor(private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun addUserData(
            graphQLContext: MyGraphQLContext,
            username: String,
            contact: String,
            description: String): OrgUserData {

        logger.debug("request to add user data received")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add new location")
        return userDataService.addUserData(
                loggedInUser = loggedInUser,
                username,
                contact,
                description,
        )
    }
}