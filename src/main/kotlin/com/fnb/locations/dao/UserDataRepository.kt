package com.fnb.locations.dao

import com.fnb.locations.model.OrgUserData
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataRepository : CoroutineCrudRepository<OrgUserData, Int> {
}