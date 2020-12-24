package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.dao.LocationTagBridgeRepository
import com.fnb.locations.dao.LocationTagRepository
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.model.LocationTagBridge
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.service.LocationTagService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocationTagService(
        @Autowired private val locationTagRepository: LocationTagRepository,
        @Autowired private val locationTagBridgeRepository: LocationTagBridgeRepository,
        @Autowired private val permissionService: PermissionService) : LocationTagService {

    override suspend fun addTag(loggedInUser: LoggedInUser, tagName: String, description: String): LocationTag {
        val tag = LocationTag(tagName = tagName, description = description, ownerId = loggedInUser.id)
        return locationTagRepository.save(tag)
    }

    override suspend fun getAllTags(): List<LocationTag> {
        return locationTagRepository.findAll().toList()
    }

    override suspend fun getTagsByLocation(locationId: Int): List<LocationTag> {
        return locationTagRepository.findTagsByLocationId(locationId).toList()
    }

    override suspend fun getTag(id: Int): LocationTag {
        return locationTagRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such location tag")
    }

    override suspend fun updateTag(loggedInUser: LoggedInUser, id: Int, tagName: String?, description: String?): LocationTag {

        val locationTag = locationTagRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such location tag")
        permissionService.authorizeLocationTagAction(loggedInUser = loggedInUser, locationTag)

        val updatedTag = locationTag.copy(
                tagName = tagName ?: locationTag.tagName,
                description = description ?: locationTag.description)
        return locationTagRepository.save(updatedTag)
    }

    override suspend fun deleteTag(loggedInUser: LoggedInUser, id: Int): LocationTag {
        val locationTag = locationTagRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such location tag")
        permissionService.authorizeLocationTagAction(loggedInUser, locationTag)
        locationTagRepository.deleteById(id = id)
        return locationTag
    }


    override suspend fun assignTags(loggedInUser: LoggedInUser, tags: List<LocationTag>, location: Location): List<LocationTag> {
        permissionService.authorizeLocationAction(loggedInUser, location)
        assert(location.id != null)
        return coroutineScope {
            for (tag in tags) {
                // TODO replace this with batching
                locationTagBridgeRepository.addLink(
                        tagId = tag.id ?: throw IllegalArgumentException("Tag must have ID"),
                        locationId = location.id!!)
            }
            tags
        }
    }


    override suspend fun unassignTags(loggedInUser: LoggedInUser, tags: List<LocationTag>, location: Location): List<LocationTag> {
        permissionService.authorizeLocationAction(loggedInUser, location)
        assert(location.id != null)
        return coroutineScope {
            for (tag in tags) {
                // TODO replace this with batching
                locationTagBridgeRepository.deleteLink(
                        tagId = tag.id ?: throw IllegalArgumentException("Tag must have ID"),
                        locationId = location.id!!)
            }
            tags
        }
    }
}