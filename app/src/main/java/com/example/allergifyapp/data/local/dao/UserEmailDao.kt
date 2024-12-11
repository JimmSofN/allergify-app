package com.example.allergifyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allergifyapp.data.local.model.UserEmail

@Dao
interface UserEmailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEmail(userEmail: UserEmail)

    @Query("SELECT * FROM user_email_table")
    fun getUserEmail(): LiveData<List<UserEmail>>
}