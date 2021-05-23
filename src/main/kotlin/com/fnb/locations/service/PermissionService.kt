package com.fnb.locations.service

import com.fnb.locations.model.*

interface PermissionService {
    suspend fun authorizeUserAction(loggedInUser: LoggedInUser, user: OrgUser)
    suspend fun authorizeUserDataAction(loggedInUser: LoggedInUser, userData: OrgUserData)
    suspend fun authorizeLocationAction(loggedInUser: LoggedInUser, location: Location)
    suspend fun authorizeLocationTagAction(loggedInUser: LoggedInUser, locationTag: LocationTag)
    suspend fun authorizeImageAction(loggedInUser: LoggedInUser, imageUrl: ImageUrl)
}