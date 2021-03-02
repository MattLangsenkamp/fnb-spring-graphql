package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.customExceptions.IllegalArgumentException
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.service.impl.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDataQuery
@Autowired constructor(private val userDataService: UserDataService) : Query {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun userData(userDataId: Int?, orgUserId: Int?): OrgUserData {

        logger.info(userDataId.toString())
        logger.info(orgUserId.toString())
        return if (userDataId != null) {
            logger.info("request to get user data with user data id: $userDataId received")
            userDataService.getUserData(userDataId)
        } else if (orgUserId != null) {
            logger.debug("request to get user data with user data id: $userDataId received")
            userDataService.getUserDataByOrgUser(orgUserId)
        } else {
            throw IllegalArgumentException("Must provide either a userDataId or a orgUserId")
        }
    }
}