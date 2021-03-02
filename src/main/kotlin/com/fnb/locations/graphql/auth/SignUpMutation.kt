package com.fnb.locations.graphql.auth

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Tokens
import com.fnb.locations.model.UserPermissionLevel
import com.fnb.locations.service.impl.AuthService
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SignUpMutation(@Autowired private val authService: AuthService,
                     @Autowired private val userDataService: UserDataService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun signUp(email: String,
                       password: String,
                       username: String,
                       contact: String,
                       description: String,
                       picture: String): Tokens {

        logger.debug("sign up with email $email received")
        val newUser = authService.signUp(email, password, UserPermissionLevel.USER)
        userDataService.addUserData(loggedInUser = newUser.toLoggedInUser(), username,
                contact,
                description,
                picture)
        return authService.signTokens(newUser)
    }
}