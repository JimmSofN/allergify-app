package com.example.allergifyapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.allergifyapp.data.local.dao.UserEmailDao
import com.example.allergifyapp.data.local.dao.UserInfoDao
import com.example.allergifyapp.data.local.dao.UserNameDao
import com.example.allergifyapp.data.local.model.UserEmail
import com.example.allergifyapp.data.local.model.UserInfo
import com.example.allergifyapp.data.local.model.UserName

@Database(entities = [UserInfo::class, UserName::class, UserEmail::class], version = 2, exportSchema = false)
abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
    abstract fun userNameDao(): UserNameDao
    abstract fun userEmailDao(): UserEmailDao

    companion object {
        @Volatile
        private var INSTANCE: UserInfoDatabase? = null

        fun getDatabase(context: Context): UserInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserInfoDatabase::class.java,
                    "user_info_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

