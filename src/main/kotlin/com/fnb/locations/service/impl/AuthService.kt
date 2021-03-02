package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.customExceptions.InvalidCredentialsException
import com.fnb.locations.dao.UserRepository
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.Tokens
import com.fnb.locations.model.UserPermissionLevel
import com.fnb.locations.service.AuthService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.stereotype.Service
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
@Transactional
class AuthService(
        @Autowired private val env: Environment,
        @Autowired private val passwordEncoder: PasswordEncoder,
        @Autowired private val permissionService: PermissionService,
        @Autowired private val userRepository: UserRepository) : AuthService {

    private val secretKey = Keys.hmacShaKeyFor(
            env["jwt.secret"]?.toByteArray() ?: throw Exception("no JWT key found"))

    private val parser = Jwts
            .parserBuilder()
            .setSigningKey(secretKey)
            .build()

    override suspend fun signTokens(user: OrgUser): Tokens {
        val refreshToken = Jwts.builder()
                .setSubject(user.email)
                .claim("count", user.count)
                .claim("permissionLevel", user.permissionLevel.toString())
                .setId(user.id.toString())
                .setIssuedAt(Date())
                .setExpiration(
                        Date.from(Instant.now().plus(14, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact()

        val accessToken = Jwts.builder()
                .setSubject(user.email)
                .setId(user.id.toString())
                .claim("permissionLevel", user.permissionLevel.toString())
                .setIssuedAt(Date())
                .setExpiration(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact()

        return Tokens(accessToken, refreshToken)
    }

    override suspend fun verifyAndRefreshTokensOrClear(accessToken: String, refreshToken: String): Tokens {
        return try {
            parser.parseClaimsJws(accessToken)
            parser.parseClaimsJws(refreshToken)
            Tokens(accessToken, refreshToken)
        } catch (e: ExpiredJwtException) {
            try {
                val user = getLoggedInUser(refreshToken) ?: throw Exception("invalid token")
                val expectedCount = getCount(refreshToken)
                val orgUserToRefresh = userRepository.findById(user.id)
                if (orgUserToRefresh?.count != expectedCount) throw Exception("tokens do not match")
                signTokens(orgUserToRefresh)
            } catch (e: Exception) {
                Tokens("", "")
            }
        } catch (e: Exception) {
            Tokens("", "")
        }
    }

    override suspend fun getLoggedInUser(accessToken: String): LoggedInUser? {
        return try {
            val claims = parser.parseClaimsJws(accessToken).body

            LoggedInUser(
                    id = claims.id.toInt(),
                    email = claims.subject,
                    permissionLevel = UserPermissionLevel.valueOf(claims["permissionLevel"].toString()))
        } catch (e: Exception) {
            null
        }
    }


    override suspend fun signUp(email: String, password: String, permissionLevel: UserPermissionLevel): OrgUser {
        val user = OrgUser(
                email = email,
                userPassword = passwordEncoder.encode(password),
                count = 0,
                permissionLevel = UserPermissionLevel.USER // change later
        )
        return userRepository.save(user)
    }

    override suspend fun signIn(email: String, password: String): Tokens {
        val user = userRepository.findByEmail(email) ?: throw FailedToFetchResourceException("No user with that email")
        if (passwordEncoder.matches(password, user.userPassword)) return signTokens(user)
        else throw InvalidCredentialsException("username or password is not correct")
    }

    override suspend fun  deleteUser(loggedInUser: LoggedInUser, id: Int): LoggedInUser {
        val user = userRepository.findById(id) ?: throw FailedToFetchResourceException("No user with that id")
        permissionService.authorizeUserAction(loggedInUser, user)
        userRepository.deleteById(id)
        return user.toLoggedInUser()
    }

    private suspend fun getCount(refreshToken: String): Int {
        val claims = parser.parseClaimsJws(refreshToken).body
        return claims["count"].toString().toInt()
    }
}