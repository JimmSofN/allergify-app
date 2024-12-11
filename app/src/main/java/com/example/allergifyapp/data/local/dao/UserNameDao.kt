package com.example.allergifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.allergifyapp.data.local.model.UserName

@Dao
interface UserNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserName(userName: UserName)

    @Update
    suspend fun updateUserName(userName: UserName)

    @Query("SELECT * FROM user_name_table WHERE id = 1 LIMIT 1")
    suspend fun getUserNameById(): UserName?

    @Query("SELECT * FROM user_name_table")
    fun getUserName(): LiveData<List<UserName>>
}