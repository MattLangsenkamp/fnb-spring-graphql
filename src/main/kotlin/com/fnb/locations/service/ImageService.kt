package com.fnb.locations.service

import com.fnb.locations.model.ImageUrl
import com.fnb.locations.model.LoggedInUser

interface ImageService {
    suspend fun uploadImageBase64(loggedInUser: LoggedInUser, img: String, type: String, ownerId: Int, resourceOwner: Any): ImageUrl
    suspend fun replaceImageBase64(loggedInUser: LoggedInUser, img: String, id: Int, type: String, resourceOwner: Any): ImageUrl
    suspend fun deleteImage(loggedInUser: LoggedInUser, id: Int, type: String, resourceOwner: Any): ImageUrl
    suspend fun uploadImagePreSignedUrl(loggedInUser: LoggedInUser, mimeType: String, type: String, ownerId: Int, resourceOwner: Any): ImageUrl
    suspend fun replaceImagePreSignedUrl(loggedInUser: LoggedInUser, mimeType: String, id: Int, type: String, resourceOwner: Any): ImageUrl
    suspend fun deleteImagesByUser(loggedInUser: LoggedInUser, userId: Int, resourceOwner: Any): List<ImageUrl>
    suspend fun deleteImagesByLocation(loggedInUser: LoggedInUser, locId: Int, resourceOwner: Any): List<ImageUrl>
    suspend fun getImage(imageId: Int): ImageUrl
    suspend fun getAllImages(type: String): List<ImageUrl>
    suspend fun getAllImagesByOwner(ownerId: Int, type: String): List<ImageUrl>
}