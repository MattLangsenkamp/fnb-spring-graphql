package com.fnb.locations.dao

import com.fnb.locations.model.ImageUrl
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageUrlRepository : CoroutineCrudRepository<ImageUrl, Int> {
    @Query("select * from image_url iu where iu.owner_id = :id and iu.type = :type")
    suspend fun findByOwnerId(id: Int, type: String): List<ImageUrl>

    @Query("select * from image_url iu where iu.type = :type")
    suspend fun findByType(type: String): List<ImageUrl>

    @Modifying
    @Query("delete from image_url iu where iu.id = :id")
    suspend fun deleteByImageUrlId(id: Int)
}