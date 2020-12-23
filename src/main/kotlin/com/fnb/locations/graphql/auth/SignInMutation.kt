package com.fnb.locations.graphql.auth

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Tokens
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.AuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SignInMutation(@Autowired val authService: AuthService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun signIn(graphQLContext: MyGraphQLContext, email: String, password: String): Tokens {

        logger.debug("sign in with email $email received")
        return authService.signIn(email, password)
    }
}