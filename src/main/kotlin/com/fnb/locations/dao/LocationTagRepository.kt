package com.fnb.locations.dao

import com.fnb.locations.model.LocationTag
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationTagRepository : CoroutineCrudRepository<LocationTag, Int> {
    @Query("select * from location_tag lt, \"location\" l, location_tag_bridge ltb where l.id = $1 and lt.id = ltb.tag_id and l.id = ltb.location_id")
    suspend fun findTagsByLocationId(locationId: Int): Flow<LocationTag>
}