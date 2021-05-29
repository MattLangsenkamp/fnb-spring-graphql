package com.fnb.locations.dao

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {

    @Value("\${minio.access.key}")
    lateinit var accessKey: String

    @Value("\${minio.secret.key}")
    lateinit var secretKey: String

    @Value("\${minio.external.url}")
    lateinit var minioUrl: String

    @Bean
    fun generateMinioClient(): MinioClient {
        return try {
            MinioClient
                    .builder()
                    .credentials(accessKey, secretKey)
                    .endpoint(minioUrl, 9000, false)
                    .build()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }
}
