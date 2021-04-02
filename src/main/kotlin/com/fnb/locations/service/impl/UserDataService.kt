package com.fnb.locations.service.impl

import com.fnb.locations.customExceptions.FailedToFetchResourceException
import com.fnb.locations.dao.UserDataRepository
import com.fnb.locations.model.LoggedInUser
import com.fnb.locations.model.OrgUserData
import com.fnb.locations.service.UserDataService
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional
class UserDataService
@Autowired constructor(
        private val userDataRepository: UserDataRepository,
        private val imageService: ImageService,
        private val permissionService: PermissionService,
        private val locationService: LocationService) : UserDataService {

    @Transactional
    override suspend fun addUserData(
            loggedInUser: LoggedInUser,
            username: String,
            contact: String,
            description: String,
            picture: String): OrgUserData {

        val pictureURI = imageService.uploadImage(loggedInUser, picture)
        val userData = OrgUserData(
                orgUserId = loggedInUser.id,
                username = username,
                contact = contact,
                description = description,
                pictureURI = pictureURI)

        val savedUserData = userDataRepository.save(userData)
        savedUserData.setLocs(locationService.getLocationsByUser(loggedInUser.id))
        return savedUserData
    }

    override suspend fun deleteUserData(loggedInUser: LoggedInUser, id: Int): OrgUserData {
        val userData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        permissionService.authorizeUserDataAction(loggedInUser, userData)
        userDataRepository.deleteById(id)
        return userData
    }

    override suspend fun getAllUserData(): List<OrgUserData> {
        val userData = userDataRepository.findAll().toList()
        for (ud in userData) {
            ud.setLocs(locationService.getLocationsByUser(ud.orgUserId))
        }
        return userData
    }

    override suspend fun getUserDataByOrgUser(orgUserId: Int): OrgUserData {
        val userData = userDataRepository.findByOrgUserId(orgUserId)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        userData.setLocs(locationService.getLocationsByUser(userData.orgUserId))
        return userData
    }

    override suspend fun getUserData(id: Int): OrgUserData {
        val userData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        userData.setLocs(locationService.getLocationsByUser(userData.orgUserId))
        return userData
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
        val newUserData = currentUserData.copy(
                username = username ?: currentUserData.username,
                contact = contact ?: currentUserData.contact,
                description = description ?: currentUserData.description,
                pictureURI = newPicture,
        )
        val finalUserData = userDataRepository.save(newUserData)
        finalUserData.setLocs(locationService.getLocationsByUser(currentUserData.orgUserId))
        return finalUserData
    }
}