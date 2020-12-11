package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant
import org.springframework.data.relational.core.mapping.Table


@Table
data class Location(
        @Id
        @Column("_id")
        val id: Int? = null,
        @Column("_name")
        val name: String,
        val friendlyName: String,
        val description: String,
        val latitude: Double,
        val longitude: Double,
        val pictureURI: String,
        val locationOwner: String,
        val needsCleaning: Boolean,
        @Transient
        var typeTags: List<LocationTag>?,
        val creationDateTime: String = Instant.now().toString(),
)

