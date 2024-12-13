package com.example.allergifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.allergifyapp.data.local.model.UserEmail

@Dao
interface UserEmailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEmail(userEmail: UserEmail)

    @Update
    suspend fun updateUserEmail(userEmail: UserEmail)

    @Query("SELECT * FROM user_email_table WHERE id = 1 LIMIT 1")
    suspend fun getUserEmailById(): UserEmail?

    @Query("SELECT * FROM user_email_table")
    fun getUserEmail(): LiveData<List<UserEmail>>
}