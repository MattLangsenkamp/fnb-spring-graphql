package com.fnb.locations.service

import com.fnb.locations.dao.LocationRepository
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class LocationService
@Autowired constructor(private val repo: LocationRepository, private val imageService: ImageService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
    }
     * Creates a new location entry in DynamoDB
     *
     * @param name name of location
     * @param friendlyName friendly location name (ie corner of example lane)
     * @param description description of location
     * @param latitude
     * @param longitude
     * @param picture the location of the associated picture
     * @param typeTags the type of location
     * @return newly created location on success, null on failure
     */
    @Transactional
    suspend fun addLocation(name: String,
                            friendlyName: String,
                            description: String,
                            latitude: Double,
                            longitude: Double,
                            picture: String,
                            typeTags: List<LocationTag>
    ): Location {
        logger.debug("Creating new location named $name")
        val pictureURI = "goober"//imageService.uploadImage(picture)
        val location = Location(
                name = name,
                friendlyName = friendlyName,
                description = description,
                latitude = latitude,
                longitude = longitude,
                pictureURI = pictureURI,
                needsCleaning = false,
                locationOwner = "who ever is logged in",
                typeTags = typeTags,
        )
        return repo.save(location)
    }

    /**
     * Retrieves a location based on the id provided
     *
     * @param id String representing the id
     * @return Location Data Class representing intended location
     * or null if location with specified id does not exist
     */
    suspend fun getLocation(id: Int): Location {
        return repo.findById(id) ?: throw Exception("no such Location")
    }

    /**
     * Retrieves all locations
     *
     * @return A list of Location data classes
     */
    suspend fun getAllLocations(): List<Location> {
        logger.debug("Retrieving all locations")
        return repo.findAll().toList()
    }

    /**
     * updates the specified location, or creates a new one if the provided id is not attached to a entry
     *
     * @param location the location to update, 0 or more fields my be changed with the exception of the id
     * @return the new location object on success, null on failure
     */
    suspend fun updateLocation(id: Int,
                               name: String?,
                               friendlyName: String?,
                               description: String?,
                               latitude: Double?,
                               longitude: Double?,
                               picture: String?,
                               typeTags: List<LocationTag>?): Location {
        val location = repo.findByLocationOwner(id) ?: throw Exception()
        // TODO permission service stuff
        val pictureURI = if (picture != null) imageService.uploadImage(picture) else null
        val updatedLocation = location.copy(
                name = name ?: location.name,
                friendlyName = friendlyName ?: location.friendlyName,
                description = description ?: location.description,
                latitude = latitude ?: location.latitude,
                longitude = longitude ?: location.longitude,
                pictureURI = pictureURI ?: location.pictureURI,
                typeTags = typeTags ?: location.typeTags
        )
        return repo.save(updatedLocation)
    }

    /**
     * Deletes a location entry from dynamo
     *
     * @param id guid identifier for entry to delete
     * @return the deleted location if successful, null otherwise
     */
    suspend fun deleteLocation(id: Int): Location {

        val currentLocation = repo.findById(id) ?: throw Exception()
        // TODO permission service stuff
        repo.deleteById(id)
        return currentLocation
    }
}