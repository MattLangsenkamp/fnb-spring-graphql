package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.InsufficientPermissionsException
import com.fnb.locations.model.*
import com.fnb.locations.service.PermissionService
import org.springframework.stereotype.Service

@Service
class PermissionService : PermissionService {

    override suspend fun authorizeUserAction(loggedInUser: LoggedInUser, user: OrgUser) {
        if (loggedInUser.id == user.id) return
        if (loggedInUser.permissionLevel == UserPermissionLevel.ADMIN ||
                loggedInUser.permissionLevel == UserPermissionLevel.SUPER_ADMIN) return
        throw InsufficientPermissionsException("Do not have permission to touch user")
    }

    override suspend fun authorizeUserDataAction(loggedInUser: LoggedInUser, userData: OrgUserData) {
        if (loggedInUser.id == userData.orgUserId) return
        if (loggedInUser.permissionLevel == UserPermissionLevel.ADMIN ||
                loggedInUser.permissionLevel == UserPermissionLevel.SUPER_ADMIN) return
        throw InsufficientPermissionsException("Do not have permission to touch user data")
    }

    override suspend fun authorizeLocationAction(loggedInUser: LoggedInUser, location: Location) {
        if (loggedInUser.id == location.locationOwner) return
        if (loggedInUser.permissionLevel == UserPermissionLevel.ADMIN ||
                loggedInUser.permissionLevel == UserPermissionLevel.SUPER_ADMIN) return
        throw InsufficientPermissionsException("Do not have permission to touch location")
    }

    override suspend fun authorizeLocationTagAction(loggedInUser: LoggedInUser, locationTag: LocationTag) {
        if (loggedInUser.id == locationTag.ownerId) return
        if (loggedInUser.permissionLevel == UserPermissionLevel.ADMIN ||
                loggedInUser.permissionLevel == UserPermissionLevel.SUPER_ADMIN) return
        throw InsufficientPermissionsException("Do not have permission to touch tag")
    }

    override suspend fun authorizeImageAction(loggedInUser: LoggedInUser, imageUrl: ImageUrl) {
        if (loggedInUser.id == imageUrl.ownerId) return
        if (loggedInUser.permissionLevel == UserPermissionLevel.ADMIN ||
                loggedInUser.permissionLevel == UserPermissionLevel.SUPER_ADMIN) return
        throw InsufficientPermissionsException("Do not have permission to touch image")
    }

}