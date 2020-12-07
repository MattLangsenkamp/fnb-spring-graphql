package com.fnb.locations.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        @Id
        val id: UUID,

        @Column(name = "password")
        val password: ByteArray,

        @Column(name = "count")
        val count: Int,

        @Column(name = "permissionLevel")
        val permissionLevel: UserPermissionLevel
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

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
