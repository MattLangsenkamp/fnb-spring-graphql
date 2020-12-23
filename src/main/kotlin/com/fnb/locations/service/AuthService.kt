package com.fnb.locations.service

import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.Tokens
import com.fnb.locations.model.UserPermissionLevel

interface AuthService {
    suspend fun signTokens(user: OrgUser): Tokens
    suspend fun verifyAndRefreshTokensOrClear(accessToken: String, refreshToken: String): Tokens
    suspend fun getLoggedInUser(accessToken: String): LoggedInUser?
    suspend fun signUp(email: String, password: String, permissionLevel: UserPermissionLevel): Tokens
    suspend fun signIn(email: String, password: String): Tokens
}