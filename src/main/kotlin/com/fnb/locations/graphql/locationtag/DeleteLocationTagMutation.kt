package com.fnb.locations.graphql.locationtag

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.customExceptions.NotLoggedInExceptionException
import com.fnb.locations.model.LocationTag
import com.fnb.locations.security.MyGraphQLContext
import com.fnb.locations.service.impl.LocationTagService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DeleteLocationTagMutation
@Autowired constructor(private val locationTagService: LocationTagService) : Mutation {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun deleteLocationTag(graphQLContext: MyGraphQLContext, id: Int): LocationTag {

        val loggedInUser = graphQLContext.loggedInUser
                ?: throw NotLoggedInExceptionException("Log in to add new location")

        return locationTagService.deleteTag(loggedInUser, id)
    }
}