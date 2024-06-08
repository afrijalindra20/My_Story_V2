package com.dicoding.picodiploma.mystory2.view.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.data.api.respone.StoryResponse
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import com.dicoding.picodiploma.mystory2.data.database.StoryDatabase
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediatorTest {

    private val mockApi: ApiService = FakeApiService()
    private val mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            mockApi,
            mockDb
        )
        val pagingState = PagingState<Int, ListStoryItem>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {

    override suspend fun getStories(page: Int, size: Int): StoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "https://example.com/image$i.jpg",
                "2023-05-31T12:00:00.000Z",
                "Author $i",
                "Description $i",
                0.0,
                i.toString(),
                0.0
            )
            items.add(story)
        }
        return StoryResponse(
            items.subList((page - 1) * size, (page - 1) * size + size),
            false,
            null
        )
    }

    override suspend fun getStoryDetail(id: String, token: String) = TODO("Not yet implemented")
    override suspend fun uploadStory(file: MultipartBody.Part, description: RequestBody, lat: Double?, lon: Double?) = TODO("Not yet implemented")
    override suspend fun getStoriesWithLocation(location: Int, token: String) = TODO("Not yet implemented")
    override suspend fun register(name: String, email: String, password: String) = TODO("Not yet implemented")
    override suspend fun login(email: String, password: String) = TODO("Not yet implemented")
}