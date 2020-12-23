package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDatasQuery
@Autowired constructor(private val userDataService: UserDataService) : Query {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun userDatas(): List<OrgUserData> {
        logger.debug("request to get all user datas received")
        return userDataService.getAllUserData()
    }
}