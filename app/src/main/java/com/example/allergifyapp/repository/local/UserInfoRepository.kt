package com.example.allergifyapp.repository.local

import androidx.lifecycle.LiveData
import com.example.allergifyapp.data.local.dao.UserEmailDao
import com.example.allergifyapp.data.local.dao.UserInfoDao
import com.example.allergifyapp.data.local.dao.UserNameDao
import com.example.allergifyapp.data.local.model.UserEmail
import com.example.allergifyapp.data.local.model.UserInfo
import com.example.allergifyapp.data.local.model.UserName
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val userInfoDao: UserInfoDao,
    private val userNameDao: UserNameDao,
    private val userEmailDao: UserEmailDao
) {
    fun getUserInfo(): LiveData<List<UserInfo>> {
        return userInfoDao.getUserInfo()
    }

    suspend fun insertOrUpdateUserInfo(userInfo: UserInfo) {
        val existingUserInfo = userInfoDao.getUserInfoById()
        if (existingUserInfo != null) {
            userInfoDao.updateUserInfo(userInfo)
        } else {
            userInfoDao.insertUserInfo(userInfo)
        }
    }

    fun getUserName(): LiveData<List<UserName>> {
        return userNameDao.getUserName()
    }

    suspend fun insertOrUpdateUserName(userName: UserName) {
        val existingUserName = userNameDao.getUserNameById()
        if (existingUserName != null) {
            userNameDao.updateUserName(userName)
        } else {
            userNameDao.insertUserName(userName)
        }
    }

    fun getUserEmail(): LiveData<List<UserEmail>> {
        return userEmailDao.getUserEmail()
    }

    suspend fun insertUserEmail(userEmail: UserEmail) {
        userEmailDao.insertUserEmail(userEmail)
    }
}