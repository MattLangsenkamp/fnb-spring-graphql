package com.fnb.locations.service

import com.fnb.locations.dao.UserRepository
import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.Tokens
import com.fnb.locations.model.UserPermissionLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository,
                  @Autowired private val passwordEncoder: PasswordEncoder) {

    suspend fun findByEmail(email: String): OrgUser {
        return userRepository.findByEmail(email)
    }

    suspend fun signUp(email: String, password: String, permissionLevel: UserPermissionLevel = UserPermissionLevel.USER): Tokens {
        val user = OrgUser(email = email, password = passwordEncoder.encode(password), count = 0, permissionLevel = permissionLevel)
        userRepository.save(user)
        return Tokens("", "")
    }

    suspend fun signIn(email: String, password: String): Tokens {
        return Tokens("", "")
    }
}