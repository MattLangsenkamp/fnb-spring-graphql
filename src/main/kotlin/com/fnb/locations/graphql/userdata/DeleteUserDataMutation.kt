package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.UserData
import com.fnb.locations.service.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeleteUserDataMutation
@Autowired constructor(private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun deleteUserData(id: UUID): UserData {
        return userDataService.deleteUserData(id)
    }
}