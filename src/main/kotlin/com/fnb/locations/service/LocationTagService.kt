package com.fnb.locations.service

import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.model.LoggedInUser

interface LocationTagService {
    suspend fun addTag(loggedInUser: LoggedInUser, tagName: String, description: String): LocationTag
    suspend fun getAllTags(): List<LocationTag>
    suspend fun getTagsByLocation(locationId: Int): List<LocationTag>
    suspend fun getTag(id: Int): LocationTag
    suspend fun updateTag(loggedInUser: LoggedInUser, id: Int, tagName: String?, description: String?): LocationTag
    suspend fun deleteTag(loggedInUser: LoggedInUser, id: Int): LocationTag

    /**
     * assigns tags and returns tags that were successfully assigned
     */
    suspend fun assignTags(loggedInUser: LoggedInUser, tags: List<LocationTag>, location: Location): List<LocationTag>
    
    /**
     * unassigns tags and returns tags that were successfully unassigned
     */
    suspend fun unassignTags(loggedInUser: LoggedInUser, tags: List<LocationTag>, location: Location): List<LocationTag>
}