package com.fnb.locations.dao

import com.fnb.locations.model.LocationTag
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationTagRepository : CoroutineCrudRepository<LocationTag, Int> {
    @Query("select lt.*\n" +
            "from \"location\" l \n" +
            "join location_tag_bridge ltb \n" +
            "on l.id = ltb.location_id \n" +
            "join location_tag lt\n" +
            "on lt.id = ltb.tag_id ")
    suspend fun findTagsByLocationId(locationId: Int): Flow<LocationTag>
}