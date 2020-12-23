package com.fnb.locations.model

data class LoggedInUser(
        val id: Int,
        val email: String,
        val permissionLevel: UserPermissionLevel)