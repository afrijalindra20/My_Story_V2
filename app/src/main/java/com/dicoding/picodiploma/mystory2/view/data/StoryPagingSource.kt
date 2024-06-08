package com.dicoding.picodiploma.mystory2.view.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getStories(position, params.loadSize)
            LoadResult.Page(
                data = response.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.listStory.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val INITIAL_PAGE_INDEX = 1
    }
}