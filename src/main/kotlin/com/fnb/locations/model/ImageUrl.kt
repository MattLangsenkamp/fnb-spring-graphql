package com.fnb.locations.model


import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table

@Table
data class ImageUrl(
        @Id
        val id: Int? = null,
        val ownerId: Int,
        val imageUri: String,
        val type: String,
        val presignedUrl: String = "no"
)







