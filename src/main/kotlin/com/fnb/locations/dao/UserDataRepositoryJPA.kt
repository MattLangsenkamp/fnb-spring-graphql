package com.fnb.locations.dao;

import com.fnb.locations.model.UserData
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface UserDataRepositoryJPA : CoroutineCrudRepository<UserData, UUID> {
    fun getByUsername(username: String): UserData
}
