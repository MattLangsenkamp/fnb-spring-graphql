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
            description: String): OrgUserData {

        val userData = OrgUserData(
                orgUserId = loggedInUser.id,
                username = username,
                contact = contact,
                description = description)

        val savedUserData = userDataRepository.save(userData)
        savedUserData.setUrls(
                imageService.getAllImagesByOwner(savedUserData.orgUserId, type = "orgUser"))
        savedUserData.setLocs(locationService.getLocationsByUser(loggedInUser.id))
        return savedUserData
    }

    override suspend fun deleteUserData(loggedInUser: LoggedInUser, id: Int): OrgUserData {
        val userData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        permissionService.authorizeUserDataAction(loggedInUser, userData)

        val locations = locationService.getLocationsByUser(userData.orgUserId)
        locations.forEach { imageService.deleteImagesByLocation(loggedInUser, it.id!!, it) }
        imageService.deleteImagesByUser(loggedInUser, userData.orgUserId, userData)
        userDataRepository.deleteById(id)

        userData.setUrls(
                imageService.getAllImagesByOwner(userData.orgUserId, type = "orgUser"))
        userData.setLocs(locations)
        return userData
    }

    override suspend fun getAllUserData(): List<OrgUserData> {
        val userData = userDataRepository.findAll().toList()
        for (ud in userData) {
            ud.setUrls(
                    imageService.getAllImagesByOwner(ud.orgUserId, type = "orgUser"))
            ud.setLocs(locationService.getLocationsByUser(ud.orgUserId))
        }
        return userData
    }

    override suspend fun getUserDataByOrgUser(orgUserId: Int): OrgUserData {
        val userData = userDataRepository.findByOrgUserId(orgUserId)
                ?: throw FailedToFetchResourceException("No such user data with that id")

        userData.setUrls(
                imageService.getAllImagesByOwner(userData.orgUserId, type = "orgUser"))
        userData.setLocs(locationService.getLocationsByUser(userData.orgUserId))
        return userData
    }

    override suspend fun getUserData(id: Int): OrgUserData {
        val userData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("No such user data with that id")
        userData.setUrls(
                imageService.getAllImagesByOwner(userData.orgUserId, type = "orgUser"))
        userData.setLocs(locationService.getLocationsByUser(userData.orgUserId))
        return userData
    }

    override suspend fun updateUserData(loggedInUser: LoggedInUser,
                                        id: Int,
                                        username: String?,
                                        contact: String?,
                                        description: String?
    ): OrgUserData {
        val currentUserData: OrgUserData = userDataRepository.findById(id)
                ?: throw FailedToFetchResourceException("no such org data with that ID")
        permissionService.authorizeUserDataAction(loggedInUser, currentUserData)

        val newUserData = currentUserData.copy(
                username = username ?: currentUserData.username,
                contact = contact ?: currentUserData.contact,
                description = description ?: currentUserData.description
        )
        val finalUserData = userDataRepository.save(newUserData)
        finalUserData.setUrls(
                imageService.getAllImagesByOwner(currentUserData.orgUserId, type = "orgUser"))
        finalUserData.setLocs(locationService.getLocationsByUser(currentUserData.orgUserId))
        return finalUserData
    }
}