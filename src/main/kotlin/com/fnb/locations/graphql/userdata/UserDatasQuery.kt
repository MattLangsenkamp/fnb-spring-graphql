package com.fnb.locations.graphql.userdata

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.UserData
import org.springframework.stereotype.Component

@Component
class UserDatasQuery: Query {
    fun userDatas(): List<UserData> {
    return listOf(UserData(
            "id",
            "username",
            "contact",
            "description",
            "picture",
            listOf("locations")
            )
        )
    }
}