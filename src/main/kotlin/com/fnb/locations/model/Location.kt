package com.fnb.locations.model

data class Location(val id: String,
                    val name: String,
                    val friendlyName: String,
                    val description: String,
                    val latitude: Double,
                    val longitude: Double,
                    val pictureURI: String,
                    val locationOwner: String,
                    val typeTags: List<String>
)

