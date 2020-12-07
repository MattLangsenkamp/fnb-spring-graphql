package com.fnb.locations.dao

import com.fnb.locations.model.Location
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface LocationRepositoryJPA : CoroutineCrudRepository<Location, UUID> {
    fun getByUser(userId: String): List<Location>
}