package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DeleteUserDataMutation
@Autowired constructor(private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun deleteUserData(graphQLContext: MyGraphQLContext, id: Int): OrgUserData {

        logger.info("received request to delete user with id: $id")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add new location")
        return userDataService.deleteUserData(loggedInUser, id)
    }
}