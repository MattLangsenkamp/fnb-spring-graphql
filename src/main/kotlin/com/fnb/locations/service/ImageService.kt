package com.fnb.locations.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ImageService {

    fun uploadImage(img: String): String {
        return "dumby"
    }
}