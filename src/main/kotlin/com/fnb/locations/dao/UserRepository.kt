package com.fnb.locations.dao

import com.fnb.locations.model.OrgUser
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<OrgUser, Int> {

    @Query(value = "select * from org_user ou where ou.email = $1")
    suspend fun findByEmail(email: String): OrgUser
}