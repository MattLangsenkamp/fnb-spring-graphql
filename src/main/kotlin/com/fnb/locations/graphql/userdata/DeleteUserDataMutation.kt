package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.UserData

class DeleteUserDataMutation: Mutation {
    fun deleteUserData(id: String): UserData {
        return UserData(
                "id",
                "username",
                "contact",
                "description",
                "picture",
                listOf("locations")
        )
    }
}