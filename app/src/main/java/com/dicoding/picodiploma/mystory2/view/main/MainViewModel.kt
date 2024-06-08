package com.dicoding.picodiploma.mystory2.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.data.database.StoryRepository
import com.dicoding.picodiploma.mystory2.data.database.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stories: Flow<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)
    val stories: Flow<PagingData<ListStoryItem>> = _stories

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}