package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*


@Table
data class OrgUserData(
        @Id @Column("_id")
        val id: Int? = null,
        @Column("org_user_id")
        val orgUserId: String,
        val username: String,
        val contact: String,
        val description: String,
        @Column("picture_uri")
        val pictureURI: String,
        @Transient
        var locations: List<Location>?
)