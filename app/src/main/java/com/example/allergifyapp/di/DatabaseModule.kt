package com.example.allergifyapp.di

import android.content.Context
import com.example.allergifyapp.data.local.dao.UserEmailDao
import com.example.allergifyapp.data.local.dao.UserInfoDao
import com.example.allergifyapp.data.local.dao.UserNameDao
import com.example.allergifyapp.data.local.db.UserInfoDatabase
import com.example.allergifyapp.repository.local.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserInfoDatabase {
        return UserInfoDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserInfoDatabase): UserInfoDao {
        return database.userInfoDao()
    }

    @Provides
    @Singleton
    fun provideUserNameDao(database: UserInfoDatabase): UserNameDao {
        return database.userNameDao()
    }

    @Provides
    @Singleton
    fun provideUserEmailDao(database: UserInfoDatabase): UserEmailDao {
        return database.userEmailDao()
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(
        userInfoDao: UserInfoDao,
        userNameDao: UserNameDao,
        userEmailDao: UserEmailDao
    ): UserInfoRepository {
        return UserInfoRepository(userInfoDao, userNameDao, userEmailDao)
    }
}