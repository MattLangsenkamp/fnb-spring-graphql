package com.fnb.locations.dao

import com.fnb.locations.model.OrgUser
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<OrgUser, Int> {
    fun findByEmail(email: String): OrgUser
}