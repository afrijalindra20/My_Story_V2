package com.dicoding.picodiploma.mystory2.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.data.database.StoryRepository
import kotlinx.coroutines.launch
import com.dicoding.picodiploma.mystory2.data.util.Result

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getStoriesWithLocation()) {
                is Result.Success -> {
                    _stories.value = result.data ?: emptyList()
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _error.value = result.error
                    _isLoading.value = false
                }
                is Result.Loading -> {
                    // Tidak perlu menangani kasus ini karena tidak ada kasus Loading dalam implementasi Anda
                }
            }
        }
    }
}