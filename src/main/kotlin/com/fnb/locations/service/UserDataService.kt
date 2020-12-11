package com.fnb.locations.service

import com.fnb.locations.dao.UserDataRepository
import com.fnb.locations.model.Location
import com.fnb.locations.model.OrgUserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserDataService
@Autowired constructor(private val repo: UserDataRepository) {
    suspend fun addUserData(
            username: String,
            contact: String,
            description: String,
            picture: String,
            locations: List<Location>): OrgUserData {

        val userData = OrgUserData(
                orgUserId = "",
                username = username,
                contact = contact,
                description = description,
                pictureURI = picture,
                locations = locations)

        return repo.save(userData)
    }

    suspend fun deleteUserData(id: Int): OrgUserData {
        return OrgUserData(id, "", "", "", "", "", emptyList())
    }

    suspend fun getAllUserData(): List<OrgUserData> {
        return emptyList()
    }

    suspend fun getUserData(id: Int): OrgUserData {
        return repo.findById(id) ?: throw Exception("Could not find user data")
    }

    suspend fun updateUserData(id: Int,
                               username: String,
                               contact: String,
                               description: String,
                               picture: String,
                               locations: List<Location>): OrgUserData {
        return OrgUserData(
                username = username,
                orgUserId = "",
                contact = contact,
                description = description,
                pictureURI = picture,
                locations = locations)

    }
}