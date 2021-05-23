package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.dao.ImageUrlRepository
import com.fnb.locations.dao.MinioRepository
import com.fnb.locations.model.ImageUrl
import com.fnb.locations.model.Location
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.service.ImageService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.time.Instant
import java.util.*

@Service
class ImageService(
        @Autowired private val minioRepository: MinioRepository,
        @Autowired private val imageUrlRepository: ImageUrlRepository,
        @Autowired private val permissionService: PermissionService) : ImageService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${minio.external.url}")
    lateinit var minioExternalUrl: String


    override suspend fun uploadImageBase64(loggedInUser: LoggedInUser, img: String, type: String, ownerId: Int, resourceOwner: Any): ImageUrl {
        logger.info("uploading image via base64 method")
        // decode base 64
        val imgBytes = Base64.getDecoder().decode(img.split(",")[1])

        // generate unique name
        val mimeType = img
                .split(",")[0]
                .split("/")[1]
                .split(";")[0]
        val id = loggedInUser.id
        val now = Instant.now().toString().replace("""[-:.]""".toRegex(), "")
        val uniqueFileName = "$id$now.$mimeType"
        val imgUrl = ImageUrl(ownerId = ownerId, imageUri = uniqueFileName, type = type, presignedUrl = "")
        checkOwnerPermission(loggedInUser, resourceOwner)
        minioRepository.uploadFile(uniqueFileName, imgBytes)

        return imageUrlRepository.save(imgUrl)
    }

    override suspend fun replaceImageBase64(loggedInUser: LoggedInUser, img: String, id: Int, type: String, resourceOwner: Any): ImageUrl {

        val imgBytes = Base64.getDecoder().decode(img.split(",")[1])
        val oldImg = getImage(id)
        checkOwnerPermission(loggedInUser, resourceOwner)
        val uniqueFileName = getUniqueFileName(oldImg.imageUri)
        minioRepository.uploadFile(uniqueFileName, imgBytes)

        val updatedImage = ImageUrl(
                ownerId = id,
                imageUri = getUrl(uniqueFileName),
                presignedUrl = "none",
                type = type)
        return imageUrlRepository.save(updatedImage)
    }

    override suspend fun deleteImage(loggedInUser: LoggedInUser, id: Int, type: String, resourceOwner: Any): ImageUrl {

        val img = getImage(id)
        checkOwnerPermission(loggedInUser, resourceOwner)

        minioRepository.deleteFile(getUniqueFileName(img.imageUri))
        imageUrlRepository.deleteByImageUrlId(img.id!!)
        return img
    }

    override suspend fun uploadImagePreSignedUrl(loggedInUser: LoggedInUser, mimeType: String, type: String, ownerId: Int, resourceOwner: Any): ImageUrl {

        val id = loggedInUser.id
        val now = Instant.now().toString().replace("""[-:.]""".toRegex(), "")
        val uniqueFileName = "$id$now.$mimeType"

        val imgUrl = ImageUrl(
                ownerId = ownerId,
                imageUri = getUrl(uniqueFileName),
                type = type,
                presignedUrl = "")

        checkOwnerPermission(loggedInUser, resourceOwner)
        val preSignedUrl = minioRepository.getPreSignedUrl(name = uniqueFileName)
        val finalImgUrl = imgUrl.copy(presignedUrl = preSignedUrl)

        return imageUrlRepository.save(finalImgUrl)
    }

    override suspend fun replaceImagePreSignedUrl(loggedInUser: LoggedInUser, mimeType: String, id: Int, type: String, resourceOwner: Any): ImageUrl {

        val img = getImage(id)
        checkOwnerPermission(loggedInUser, resourceOwner)
        val uniqueFileName = getUniqueFileName(img.imageUri)
        val preSignedUrl = minioRepository.getPreSignedUrl(name = uniqueFileName)

        val updatedImage = ImageUrl(
                id = id,
                ownerId = img.ownerId,
                imageUri = getUrl(uniqueFileName),
                presignedUrl = preSignedUrl,
                type = type)

        return imageUrlRepository.save(updatedImage)
    }

    override suspend fun deleteImagesByUser(loggedInUser: LoggedInUser, userId: Int, resourceOwner: Any): List<ImageUrl> {
        checkOwnerPermission(loggedInUser, resourceOwner)

        val userImages = imageUrlRepository.findByOwnerId(userId, "orgUser")
        userImages.map {
            imageUrlRepository.deleteByImageUrlId(it.id!!)
            // TODO delete in minio
        }
        return userImages
    }

    override suspend fun deleteImagesByLocation(loggedInUser: LoggedInUser, locId: Int, resourceOwner: Any): List<ImageUrl> {
        checkOwnerPermission(loggedInUser, resourceOwner)

        val locationImages = imageUrlRepository.findByOwnerId(locId, "location")
        locationImages.map {
            imageUrlRepository.deleteByImageUrlId(it.id!!)
            // TODO delete in minio
        }
        return locationImages
    }

    override suspend fun getImage(imageId: Int): ImageUrl {
        return imageUrlRepository.findById(imageId)
                ?: throw FailedToFetchResourceException("No such location image with that id")
    }

    override suspend fun getAllImages(type: String): List<ImageUrl> {
        return imageUrlRepository.findByType(type)
    }

    override suspend fun getAllImagesByOwner(ownerId: Int, type: String): List<ImageUrl> {
        return imageUrlRepository.findByOwnerId(ownerId, type)
    }

    private suspend fun getUrl(uniqueFileName: String): String {
        return "$minioExternalUrl/pictures/$uniqueFileName"
    }

    private suspend fun getUniqueFileName(url: String): String {
        return url.removePrefix("$minioExternalUrl/pictures/")
    }

    private suspend fun checkOwnerPermission(loggedInUser: LoggedInUser, resourceOwner: Any) {

        when (resourceOwner) {
            is Location -> permissionService.authorizeLocationAction(loggedInUser, resourceOwner)
            is OrgUserData -> permissionService.authorizeUserDataAction(loggedInUser, resourceOwner)
        }
    }
}