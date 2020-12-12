package com.fnb.locations.security

import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.UserPermissionLevel
import com.fnb.locations.service.UserService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ServiceReactiveUserDetailsService(@Autowired private val userService: UserService) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        return mono {
            val orgUser = userService.findByEmail(username ?: "default")
            CustomUser(orgUser.id ?: 0, orgUser.email, orgUser.userPassword, orgUser.count, orgUser.permissionLevel)
        }
    }

    class CustomUser(
            id: Int,
            email: String,
            password: String,
            count: Int,
            permissionLevel: UserPermissionLevel)
        : OrgUser(
            id = id,
            email = email,
            userPassword = password,
            count = count,
            permissionLevel = permissionLevel), UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return AuthorityUtils.createAuthorityList("ROLE_USER")
        }

        override fun getPassword(): String {
            return password
        }

        override fun getUsername(): String {
            return email
        }

        override fun isAccountNonExpired() = true
        override fun isAccountNonLocked() = true
        override fun isCredentialsNonExpired() = true
        override fun isEnabled() = true
    }
}