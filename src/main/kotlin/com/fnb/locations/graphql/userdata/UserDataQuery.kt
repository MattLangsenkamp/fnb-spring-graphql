package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.UserData
import com.fnb.locations.service.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDataQuery
@Autowired constructor(private val userDataService: UserDataService) : Query {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun userData(id: UUID): UserData {

        return userDataService.getUserData(id)
    }
}