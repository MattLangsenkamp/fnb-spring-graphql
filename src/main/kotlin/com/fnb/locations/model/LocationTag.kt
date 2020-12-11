package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("location_tag")
data class LocationTag(
        @Id
        val id: Int? = null,
        @Column("tag_name")
        val name: String,
        val description: String
)
