package com.fnb.locations.graphql.auth

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Tokens
import com.fnb.locations.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SignInMutation(@Autowired val userService: UserService) : Mutation {

    suspend fun signIn(email: String, password: String): Tokens {
        return userService.signIn(email, password)
    }
}