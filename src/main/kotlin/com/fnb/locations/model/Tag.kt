package com.fnb.locations.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Tag(
        @Id
        val id: UUID,

        @Column(name = "name", length = 255)
        val name: String,

        @Column(name = "description", length = 765)
        val description: String
)
