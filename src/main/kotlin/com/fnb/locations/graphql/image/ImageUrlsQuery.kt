package com.fnb.locations.graphql.image

import com.expediagroup.graphql.spring.operations.Query
import com.fnb.locations.model.ImageUrl
import com.fnb.locations.service.impl.ImageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ImageUrlsQuery
@Autowired constructor(private val imageService: ImageService) : Query {
    private val logger = LoggerFactory.getLogger(javaClass)
    suspend fun imageUrls(type: String, ownerId: Int?): List<ImageUrl> {
        
        return if (ownerId != null) {
            logger.info("getting image with ownerId $ownerId  and type $type")
            imageService.getAllImagesByOwner(ownerId = ownerId, type)
        } else {
            logger.info("getting image with type $type")
            imageService.getAllImages(type)
        }
    }
}