package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.dao.UserDataRepositoryJPA
import com.fnb.locations.model.Location
import com.fnb.locations.model.UserData
import com.fnb.locations.service.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddUserDataMutation
@Autowired constructor(private val userDataService: UserDataService) : Mutation {

    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun addUserData(
            username: String,
            contact: String,
            description: String,
            picture: String,
            locations: List<Location> = emptyList()): UserData {
        return userDataService.addUserData(
                username,
                contact,
                description,
                picture,
                locations
        )
    }
}