package com.fnb.locations.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class OrgUser(
        @Id
        val id: Int? = null,
        val password: ByteArray,
        val count: Int,
        val permissionLevel: UserPermissionLevel
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrgUser

        if (id != other.id) return false
        if (!password.contentEquals(other.password)) return false
        if (count != other.count) return false
        if (permissionLevel != other.permissionLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + count
        result = 31 * result + permissionLevel.hashCode()
        return result
    }
}
