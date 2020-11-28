package com.fnb.locations.graphql.location

import com.expediagroup.graphql.spring.operations.Mutation
import com.fnb.locations.model.Location
import com.fnb.locations.model.LocationForm
import org.springframework.stereotype.Component

@Component
class TestLocationMutation: Mutation {
    fun testLocation(form: LocationForm): LocationForm {
        return form.copy(description = "been here")
    }
}