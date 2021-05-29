package com.fnb.locations.dao

import io.minio.*
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Component
class MinioRepository(@Autowired private val minioClient: MinioClient) {

    private final val bucket: String = "pictures"

    init {
        //if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
        //    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build())
        //}
    }

    fun uploadFile(name: String, bytes: ByteArray): String {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .stream(
                                bytes.inputStream(),
                                bytes.size.toLong(),
                                -1)
                        .bucket(bucket)
                        .contentType("image")
                        .`object`(name)
                        .userMetadata(
                                mapOf("Content-type" to "image"))
                        .headers(
                                mapOf("Content-type" to "image//*"))
                        .build())
        return name
    }

    fun deleteFile(name: String): String {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).`object`(name).build())
        return name
    }

    fun getPreSignedUrl(name: String): String {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(bucket)
                        .`object`(name)
                        .expiry(1, TimeUnit.DAYS)
                        .extraQueryParams(mapOf("Content-type" to "image//*"))
                        .build())
    }
}