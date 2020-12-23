package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.dao.LocationRepository
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.service.LocationService
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LocationService
@Autowired constructor(
        private val repo: LocationRepository,
        private val imageService: ImageService,
        private val locationTagService: LocationTagService,
        private val permissionService: PermissionService) : LocationService {

    private val logger = LoggerFactory.getLogger(javaClass)


    override suspend fun addLocation(loggedInUser: LoggedInUser,
                                     name: String,
                                     friendlyName: String,
                                     description: String,
                                     latitude: Double,
                                     longitude: Double,
                                     picture: String,
                                     locationTags: List<LocationTag>
    ): Location {
        logger.debug("Creating new location named $name")
        val pictureURI = "goober"//imageService.uploadImage(picture)
        val location = Location(
                locationName = name,
                friendlyName = friendlyName,
                description = description,
                latitude = latitude,
                longitude = longitude,
                pictureURI = pictureURI,
                needsCleaning = false,
                locationOwner = loggedInUser.id
        )
        val createdLocation = repo.save(location)
        createdLocation.locationTags = locationTagService
                .assignTags(
                        loggedInUser = loggedInUser,
                        tags = locationTags,
                        location = createdLocation)
        return createdLocation
    }

    override suspend fun getLocation(id: Int): Location {
        return repo.findById(id) ?: throw Exception("no such Location")
    }

    override suspend fun getLocationsByUser(id: Int): List<Location> {
        return repo.findByLocationOwner(id)
    }

    override suspend fun getAllLocations(): List<Location> {
        logger.debug("Retrieving all locations")
        return repo.findAll().toList()
    }

    override suspend fun updateLocation(loggedInUser: LoggedInUser,
                                        id: Int,
                                        name: String?,
                                        friendlyName: String?,
                                        description: String?,
                                        latitude: Double?,
                                        longitude: Double?,
                                        picture: String?,
                                        newLocationTags: List<LocationTag>?): Location {
        val location = repo.findById(id) ?: throw Exception()
        permissionService.authorizeLocationAction(loggedInUser, location)
        val pictureURI = if (picture != null) imageService.uploadImage(loggedInUser, picture) else null
        val currentLocationTags = locationTagService.getTagsByLocation(id)
        val updatedLocation = location.copy(
                locationName = name ?: location.locationName,
                friendlyName = friendlyName ?: location.friendlyName,
                description = description ?: location.description,
                latitude = latitude ?: location.latitude,
                longitude = longitude ?: location.longitude,
                pictureURI = pictureURI ?: location.pictureURI,
                locationTags = newLocationTags ?: currentLocationTags
        )
        if (newLocationTags != null) {
            val assignedTags = newLocationTags.filter { it !in currentLocationTags }
            val unassignedTags = currentLocationTags.filter { it in newLocationTags }

            locationTagService
                    .assignTags(
                            loggedInUser = loggedInUser,
                            tags = assignedTags,
                            location = location)
            locationTagService
                    .unassignTags(
                            loggedInUser = loggedInUser,
                            tags = unassignedTags,
                            location = location)

            updatedLocation.locationTags = newLocationTags
        }
        return repo.save(updatedLocation)
    }

    override suspend fun deleteLocation(loggedInUser: LoggedInUser, id: Int): Location {

        val currentLocation = repo.findById(id) ?: throw FailedToFetchResourceException("Location does not exist")
        permissionService.authorizeLocationAction(loggedInUser, currentLocation)
        repo.deleteById(id)
        return currentLocation
    }
}