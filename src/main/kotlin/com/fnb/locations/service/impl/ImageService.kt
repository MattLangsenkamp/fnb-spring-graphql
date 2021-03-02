package com.fnb.locations.service.impl

import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.service.ImageService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ImageService : ImageService {

    override fun uploadImage(loggedInUser: LoggedInUser, img: String): String {
        val randInt1= Random.nextInt(from =250, until = 400)
        val randInt2= Random.nextInt(from =250, until = 400)
        return "https://placeimg.com/$randInt1/$randInt2/arch/sepia"
    }
}