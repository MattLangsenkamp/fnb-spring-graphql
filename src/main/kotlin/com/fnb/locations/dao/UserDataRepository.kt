package com.fnb.locations.dao

import com.fnb.locations.model.OrgUserData
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataRepository : CoroutineCrudRepository<OrgUserData, Int> {

    @Query("select * from org_user_data oud where oud.org_user_id = :id")
    suspend fun findByOrgUserId(id: Int): OrgUserData?
}