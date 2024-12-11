package com.example.allergifyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_name_table")
data class UserName(
    @PrimaryKey val id: Int = 1,
    val userName: String
)