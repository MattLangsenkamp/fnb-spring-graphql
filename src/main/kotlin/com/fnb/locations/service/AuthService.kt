package com.fnb.locations.service

import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.Tokens

class AuthService {

    suspend fun signTokens(user: OrgUser): Tokens {
        return Tokens("", "")
    }

    suspend fun verifyAndRefreshTokensOrNull(requestTokens: Tokens): Tokens? {
        return Tokens("", "")
    }
}