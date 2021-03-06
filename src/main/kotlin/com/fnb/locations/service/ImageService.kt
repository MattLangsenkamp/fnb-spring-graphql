package com.fnb.locations.service

import com.fnb.locations.model.LoggedInUser

interface ImageService {
    fun uploadImage(loggedInUser: LoggedInUser, img: String): String
}