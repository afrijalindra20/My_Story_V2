package com.dicoding.picodiploma.mystory2.data.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.picodiploma.mystory2.data.pref.UserPreference
import com.dicoding.picodiploma.mystory2.data.api.respone.DetailStoryResponse
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import com.dicoding.picodiploma.mystory2.data.util.Result
import com.dicoding.picodiploma.mystory2.view.data.StoryRemoteMediator

class StoryRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val database: StoryDatabase
) {
    fun getStories(): Flow<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = StoryRemoteMediator(apiService, database),
            pagingSourceFactory = { database.storyDao().getPagingSource() }
        ).flow
    }

    suspend fun getStoryDetail(id: String, token: String): Response<DetailStoryResponse> {
        return apiService.getStoryDetail(id, "Bearer $token")
    }

    suspend fun getStoriesWithLocation(): Result<List<ListStoryItem>> {
        return try {
            val token = getUserToken()
            val response = apiService.getStoriesWithLocation(1, "Bearer $token")
            Result.Success(response.listStory)
        } catch (e: HttpException) {
            Result.Error(e.message())
        } catch (e: IOException) {
            Result.Error(e.message.toString())
        }
    }

    private suspend fun getUserToken(): String {
        return userPreference.getSession().first()?.token ?: ""
    }

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference, database: StoryDatabase): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StoryRepository(apiService, userPreference, database)
                INSTANCE = instance
                instance
            }
        }
    }
}