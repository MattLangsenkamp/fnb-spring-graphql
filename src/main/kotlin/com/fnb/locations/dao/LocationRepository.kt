package com.fnb.locations.dao

import com.fnb.locations.model.Location
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : CoroutineCrudRepository<Location, Int> {
    suspend fun findByLocationOwner(id: Int): List<Location>
}