package com.fnb.locations.dao

import com.fnb.locations.model.LocationTagBridge
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationTagBridgeRepository : CoroutineCrudRepository<LocationTagBridge, LocationTagBridge> {

    @Modifying
    @Query("insert into location_tag_bridge(location_id, tag_id) values(:locationId, :tagId)")
    suspend fun addLink(locationId: Int, tagId: Int)

    @Modifying
    @Query("delete from location_tag_bridge ltb where ltb.location_id = :locationId and ltb.tag_id = :tagId ")
    suspend fun deleteLink(locationId: Int, tagId: Int)
}