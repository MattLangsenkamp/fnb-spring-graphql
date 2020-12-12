package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
open class OrgUser(
        @Id
        val id: Int? = null,
        val email: String,
        val userPassword: String,
        val count: Int,
        val permissionLevel: UserPermissionLevel
)
