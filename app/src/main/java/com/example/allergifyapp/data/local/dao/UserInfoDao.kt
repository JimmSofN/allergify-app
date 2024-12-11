package com.example.allergifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.allergifyapp.data.local.model.UserInfo

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfo)

    @Update
    suspend fun updateUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM user_info_table WHERE id = 1 LIMIT 1")
    suspend fun getUserInfoById(): UserInfo?

    @Query("SELECT * FROM user_info_table")
    fun getUserInfo(): LiveData<List<UserInfo>>
}