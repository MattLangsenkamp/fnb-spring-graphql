package com.fnb.locations.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
data class UserData(
        @Id
        val id: UUID,

        @Column(name = "username")
        val username: String,

        @Column(name = "contact")
        val contact: String,

        @Column(name = "description")
        val description: String,

        @Column(name = "picture")
        val picture: String,

        @OneToMany
        val locations: List<Location>
)