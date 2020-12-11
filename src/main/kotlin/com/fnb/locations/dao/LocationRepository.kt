package com.fnb.locations.dao

import com.fnb.locations.model.Location
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface LocationRepository : CoroutineCrudRepository<Location, Int> {
    suspend fun findByLocationOwner(id: Int): Location?
}