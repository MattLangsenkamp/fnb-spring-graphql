package com.fnb.locations.service

import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUserData

interface UserDataService {
    suspend fun addUserData(
            loggedInUser: LoggedInUser,
            username: String,
            contact: String,
            description: String): OrgUserData

    suspend fun deleteUserData(loggedInUser: LoggedInUser, id: Int): OrgUserData

    suspend fun getAllUserData(): List<OrgUserData>

    suspend fun getUserDataByOrgUser(orgUserId: Int): OrgUserData

    suspend fun getUserData(id: Int): OrgUserData

    suspend fun updateUserData(loggedInUser: LoggedInUser,
                               id: Int,
                               username: String?,
                               contact: String?,
                               description: String?): OrgUserData
}