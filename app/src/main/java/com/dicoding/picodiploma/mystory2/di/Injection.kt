package com.dicoding.picodiploma.mystory2.di

import android.content.Context
import androidx.room.Room
import com.dicoding.picodiploma.mystory2.data.database.RegisterRepository
import com.dicoding.picodiploma.mystory2.data.pref.UserPreference
import com.dicoding.picodiploma.mystory2.view.main.dataStore
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiConfig
import com.dicoding.picodiploma.mystory2.data.database.LoginRepository
import com.dicoding.picodiploma.mystory2.data.database.StoryDatabase
import com.dicoding.picodiploma.mystory2.data.database.StoryRepository
import com.dicoding.picodiploma.mystory2.data.database.UploadRepository
import com.dicoding.picodiploma.mystory2.data.database.UserRepository


object Injection {
    fun provideRepository(context: Context, database: StoryDatabase): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        return StoryRepository.getInstance(apiService, pref, database)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideRegisterRepository(context: Context): RegisterRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        return RegisterRepository(apiService)
    }

    fun provideLoginRepository(context: Context): LoginRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        return LoginRepository(apiService)
    }
    fun provideUploadSRepository(context: Context): UploadRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        return UploadRepository.getInstance(apiService)
    }
    fun provideDatabase(context: Context): StoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StoryDatabase::class.java, "story_database"
        ).build()
    }

}