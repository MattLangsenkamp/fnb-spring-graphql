package com.fnb.locations.graphql.locationtag

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.LocationTag
import com.fnb.locations.service.impl.LocationTagService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LocationTagQuery
@Autowired constructor(private val locationTagService: LocationTagService) : Query {

    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun looationTag(id: Int): LocationTag {
        return locationTagService.getTag(id)
    }
}