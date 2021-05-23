package com.fnb.locations.graphql.image

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.ImageUrl
import com.fnb.locations.service.impl.ImageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ImageUrlQuery
@Autowired constructor(private val imageService: ImageService) : Query {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun imageUrl(id: Int): ImageUrl {

        logger.info("getting image with id $id")
        return imageService.getImage(id)
    }
}