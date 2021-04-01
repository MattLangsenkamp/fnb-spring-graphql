package com.fnb.locations.service.impl

import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.service.ImageService
import io.minio.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.time.Instant
import java.util.*
import kotlin.random.Random

@Service
class ImageService(@Autowired private val minioClient: MinioClient) : ImageService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${minio.external.url}")
    lateinit var minioExternalUrl: String

    private final val bucket: String = "pictures"

    private final val policy: String = """
        {
         "Statement": [
                 {
                     "Action": ["s3:PutObject"],
                     "Effect": "Allow",
                     "Resource": "arn:aws:s3:::pictures/*"
                 }
             ],
             "Version": "2012-10-17"
         }
    """.trimIndent()

    init {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            // add public/ prefix
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build())
            //minioClient.setBucketPolicy(
            //      SetBucketPolicyArgs.builder().bucket(bucket).config(policy).build())
        }
    }

    override fun uploadImage(loggedInUser: LoggedInUser, img: String): String {

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

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .stream(
                                imgBytes.inputStream(),
                                imgBytes.size.toLong(),
                                -1)
                        .bucket(bucket)
                        .contentType("image")
                        .`object`(uniqueFileName)
                        .userMetadata(
                                mapOf("Content-type" to "image"))
                        .headers(
                                mapOf("Content-type" to "image//*"))
                        .build())
        //send it
        //this.minioClient.uploadObject()

        return "$minioExternalUrl/pictures/$uniqueFileName"
    }

    override fun deleteImage(loggedInUser: LoggedInUser, img: String): String {
        TODO("Not yet implemented")
    }
}