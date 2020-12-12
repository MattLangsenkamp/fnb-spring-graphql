package com.fnb.locations.graphql.auth

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Tokens
import com.fnb.locations.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SignUpMutation(@Autowired private val userService: UserService) : Mutation {

    suspend fun SigmUp(email: String, password: String): Tokens {
        return userService.signUp(email, password)
    }
}