package com.fnb.locations.graphql.auth

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.AuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DeleteUserMutation(@Autowired val authService: AuthService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun deleteUser(graphQLContext: MyGraphQLContext, id: Int): LoggedInUser {

        logger.debug("delete request from user with $id received")
        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in delete user")
        return authService.deleteUser(loggedInUser, id)
    }
}