package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.dao.UserDataRepository
import com.fnb.locations.model.Location
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.service.UserDataService
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserDataService
@Autowired constructor(
        private val userDataRepository: UserDataRepository,
        private val imageService: ImageService,
        private val permissionService: PermissionService) : UserDataService {
    override suspend fun addUserData(
            loggedInUser: LoggedInUser,
            username: String,
            contact: String,
            description: String,
            picture: String,
            locations: List<Location>): OrgUserData {

        val userData = OrgUserData(
                orgUserId = loggedInUser.id,
                username = username,
                contact = contact,
                description = description,
                pictureURI = picture,
                locations = locations)

        return userDataRepository.save(userData)
    }

    override suspend fun deleteUserData(loggedInUser: LoggedInUser, id: Int): OrgUserData {
        val userData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        permissionService.authorizeUserDataAction(loggedInUser, userData)
        userDataRepository.deleteById(id)
        return userData
    }

    override suspend fun getAllUserData(): List<OrgUserData> {
        return userDataRepository.findAll().toList()
    }

    override suspend fun getUserData(id: Int): OrgUserData {
        return userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
    }

    override suspend fun updateUserData(loggedInUser: LoggedInUser,
                                        id: Int,
                                        username: String?,
                                        contact: String?,
                                        description: String?,
                                        picture: String?): OrgUserData {
        val currentUserData: OrgUserData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("no such org data with that ID")
        permissionService.authorizeUserDataAction(loggedInUser, currentUserData)
        val newPicture =
                if (picture != null)
                    imageService.uploadImage(loggedInUser, picture)
                else currentUserData.pictureURI
        return currentUserData.copy(
                username = username ?: currentUserData.username,
                contact = contact ?: currentUserData.contact,
                description = description ?: currentUserData.description,
                pictureURI = newPicture,
        )

    }
}