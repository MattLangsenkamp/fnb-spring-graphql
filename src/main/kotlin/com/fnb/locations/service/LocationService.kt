package com.fnb.locations.service

import com.fnb.locations.model.Location
import org.springframework.stereotype.Service
import java.util.*

@Service
class LocationService {

    /**
    }
     * Creates a new location entry in DynamoDB
     *
     * @param name name of location
     * @param friendlyLocation friendly location name (ie corner of example lane)
     * @param description description of location
     * @param latitude
     * @param longitude
     * @param picture the location of the associated picture
     * @param type the type of location
     * @return newly created location on success, null on failure
     */
    fun addLocation(
        name: String,
        friendlyLocation: String,
        description: String,
        latitude: Double,
        longitude: Double,
        picture: String,
        locationOwner: String,
        type: String
    )//: Location
    {
        val id: UUID = UUID.randomUUID()

    }

    /**
     * Retrieves a location based on the id provided
     *
     * @param id String representing the id
     * @return Location Data Class representing intended location
     * or null if location with specified id does not exist
     */
    fun getLocation(id: String)//: Location
    {

    }

    /**
     * Retrieves all locations
     *
     * @return A list of Location data classes
     */
    fun getAllLocations()//: List<Location?>
    {

    }

    /**
     * updates the specified location, or creates a new one if the provided id is not attached to a entry
     *
     * @param location the location to update, 0 or more fields my be changed with the exception of the id
     * @return the new location object on success, null on failure
     */
    fun updateLocation() {

    }

    /**
     * Deletes a location entry from dynamo
     *
     * @param id guid identifier for entry to delete
     * @param locationOwner the id of the user that owns the location
     * @return the deleted location if successful, null otherwise
     */
    fun deleteLocation(id: String, locationOwner: String)//: Location
    { }
}