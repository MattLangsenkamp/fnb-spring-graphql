package com.fnb.locations.model


data class LocationForm(val id: String?,
                    val name: String,
                    val friendlyName: String,
                    val description: String,
                    val latitude: Double,
                    val longitude: Double,
                    val picture: String,
                    val locationOwner: String,
                    val typeTags: List<String>
)
