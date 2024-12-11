package com.example.allergifyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info_table")
data class UserInfo(
    @PrimaryKey val id: Int = 1,
    val age: String,
    val height: String,
    val weight: String
)
