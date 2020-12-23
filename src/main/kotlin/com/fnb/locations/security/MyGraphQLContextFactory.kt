package com.fnb.locations.security

import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import com.fnb.locations.service.impl.AuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse

import org.springframework.stereotype.Component

@Component
class MyGraphQLContextFactory(@Autowired private val authService: AuthService) : GraphQLContextFactory<MyGraphQLContext> {
    private val logger = LoggerFactory.getLogger(javaClass)


    override suspend fun generateContext(
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): MyGraphQLContext {

        val tokens = authService.verifyAndRefreshTokensOrClear(
                request.headers.getFirst("AccessToken") ?: "",
                request.headers.getFirst("RefreshToken") ?: "")

        logger.info(request.headers.getFirst("RefreshToken"))
        logger.info(request.headers.getFirst("AccessToken"))

        response.headers.set("AccessToken", tokens.accessToken)
        response.headers.set("RefreshToken", tokens.refreshToken)

        return MyGraphQLContext(
                loggedInUser = authService.getLoggedInUser(tokens.accessToken)
        )
    }


}