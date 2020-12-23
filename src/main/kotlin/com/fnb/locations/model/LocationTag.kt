package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("location_tag")
data class LocationTag(
        @Id
        val id: Int? = null,
        val tagName: String,
        val description: String,
        val ownerId: Int
)
