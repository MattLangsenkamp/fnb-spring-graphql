package com.fnb.locations.dao

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.model.OrgUser
import com.fnb.locations.model.UserPermissionLevel
import com.fnb.locations.service.impl.PermissionService
import io.r2dbc.spi.Row
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient

import org.springframework.stereotype.Repository

/*@Repository
class UserRepository(private val client: DatabaseClient) {
    suspend fun findByEmail(email: String): OrgUser =
            client.sql("SELECT * FROM org_user ou WHERE ou.email = $1")
                    .bind(0, email)
                    .map { row -> asOrgUser(row) }
                    .first()
                    .awaitFirstOrNull() ?: throw FailedToFetchResourceException("no such org user with that email")

    suspend fun findById(id: Int): OrgUser =
            client.sql("SELECT * FROM org_user ou WHERE ou.id = $1")
                    .bind(0, id)
                    .map { row -> asOrgUser(row) }
                    .first()
                    .awaitFirstOrNull() ?: throw FailedToFetchResourceException("no such org user with that id")

    suspend fun save(orgUser: OrgUser): OrgUser =
            client
                    .sql("INSERT into org_user " +
                            "(email, permission_level, count, user_password) " +
                            "VALUES ($1, $2, $3, $4)")
                    .bind(0, orgUser.email)
                    .bind(1, orgUser.permissionLevel)
                    .bind(2, orgUser.count)
                    .bind(3, orgUser.userPassword)
                    .map { row -> asOrgUser(row) }
                    .first()
                    .awaitFirstOrNull() ?: throw FailedToFetchResourceException("Failed to add org user")

    suspend fun deleteById(id: Int): OrgUser =
            client.sql("DELETE from org_user ou WHERE ou.id = '3'; ")
                    .bind(0, id)
                    .map { row -> asOrgUser(row) }
                    .first()
                    .awaitFirstOrNull() ?: throw FailedToFetchResourceException("Failed to add org user")


    fun asOrgUser(row: Row): OrgUser =
            OrgUser(
                    id = row.get("id").toString().toInt(),
                    email = row.get("email").toString(),
                    userPassword = row.get("user_password").toString(),
                    count = row.get("count").toString().toInt(),
                    permissionLevel = UserPermissionLevel.valueOf(row.get("permission_level").toString()))

}*/


@Repository
interface UserRepository : CoroutineCrudRepository<OrgUser, Int> {

    @Query(value = "SELECT * FROM org_user ou WHERE ou.email = :email")
    suspend fun findByEmail(email: String): OrgUser?
}