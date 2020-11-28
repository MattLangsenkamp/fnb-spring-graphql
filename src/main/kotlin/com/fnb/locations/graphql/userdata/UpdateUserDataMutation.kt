package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.UserData
import org.springframework.stereotype.Component

@Component
class UpdateUserDataMutation : Mutation {
    fun updateUserData(id: String,
                       username: String,
                       contact: String,
                       description: String,
                       picture: String,
                       locations: List<String>): UserData {
        return UserData(
                id,
                username,
                contact,
                description,
                picture,
                locations
        )
    }
}