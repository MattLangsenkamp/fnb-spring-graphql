package com.fnb.locations.service.impl

import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.service.ImageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService : ImageService {

    override fun uploadImage(loggedInUser: LoggedInUser, img: String): String {
        return "dumby"
    }
}