package com.fnb.locations.service

import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationTag
import com.fnb.locations.model.LoggedInUser

interface LocationService {

    /**
     * Creates a new location entry in DynamoDB
     *
     * @param name name of location
     * @param friendlyName friendly location name (ie corner of example lane)
     * @param description description of location
     * @param latitude
     * @param longitude
     * @param picture the location of the associated picture
     * @param locationTags the type of location
     * @return newly created location on success, null on failure
     */
    suspend fun addLocation(loggedInUser: LoggedInUser,
                            name: String,
                            friendlyName: String,
                            description: String,
                            latitude: Double,
                            longitude: Double,
                            picture: String,
                            locationTags: List<LocationTag>): Location

    /**
     * Retrieves a location based on the id provided
     *
     * @param id String representing the id
     * @return Location Data Class representing intended location
     * or null if location with specified id does not exist
     */
    suspend fun getLocation(id: Int): Location
    suspend fun getLocationsByUser(id: Int): List<Location>

    /**
     * Retrieves all locations
     *
     * @return A list of Location data classes
     */
    suspend fun getAllLocations(): List<Location>

    /**
     * updates the specified location, or creates a new one if the provided id is not attached to a entry
     *
     * @param location the location to update, 0 or more fields my be changed with the exception of the id
     * @return the new location object on success, null on failure
     */
    suspend fun updateLocation(loggedInUser: LoggedInUser,
                               id: Int,
                               name: String?,
                               friendlyName: String?,
                               description: String?,
                               latitude: Double?,
                               longitude: Double?,
                               picture: String?,
                               newLocationTags: List<LocationTag>?): Location

    /**
     * Deletes a location entry from dynamo
     *
     * @param id guid identifier for entry to delete
     * @return the deleted location if successful, null otherwise
     */
    suspend fun deleteLocation(loggedInUser: LoggedInUser, id: Int): Location

}