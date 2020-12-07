package com.fnb.locations.model

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "location")
data class Location(
        @Id
        val id: UUID,

        @Column(name = "name")
        val name: String,

        @Column(name = "friendlyName")
        val friendlyName: String,

        @Column(name = "description")
        val description: String,

        @Column(name = "latitude")
        val latitude: Double,

        @Column(name = "longitude")
        val longitude: Double,

        @Column(name = "pictureURI")
        val pictureURI: String,

        @Column
        val locationOwner: String,
        @Column
        val needsCleaning: Boolean,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "location_tags_bridge")
        val typeTags: List<Tag> = emptyList(),

        @Column
        val creationDateTime: Instant
)

