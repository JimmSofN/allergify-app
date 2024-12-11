package com.example.allergifyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_email_table")
data class UserEmail(
    @PrimaryKey val id: Int = 1,
    val userEmail: String
)
