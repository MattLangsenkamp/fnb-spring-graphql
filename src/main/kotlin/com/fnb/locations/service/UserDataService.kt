package com.fnb.locations.service

import com.fnb.locations.dao.UserDataRepositoryJPA
import com.fnb.locations.model.Location
import com.fnb.locations.model.UserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserDataService
@Autowired constructor(private val userDataRepositoryJPA: UserDataRepositoryJPA) {
    suspend fun addUserData(
            username: String,
            contact: String,
            description: String,
            picture: String,
            locations: List<Location>): UserData {

        val userData = UserData(id = UUID.randomUUID(), username, contact, description, picture, locations)

        return userDataRepositoryJPA.save(userData)
    }

    suspend fun deleteUserData(id: UUID): UserData {
        return UserData(id, "", "", "", "", emptyList())
    }

    suspend fun getAllUserData(): List<UserData> {
        return emptyList()
    }

    suspend fun getUserData(id: UUID): UserData {
        return userDataRepositoryJPA.findById(id) ?: throw Exception("Could not find user data")
    }

    suspend fun updateUserData(id: UUID,
                               username: String,
                               contact: String,
                               description: String,
                               picture: String,
                               locations: List<Location>): UserData {
        return UserData(id = UUID.randomUUID(), username, contact, description, picture, locations)

    }
}