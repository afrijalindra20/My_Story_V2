package com.dicoding.picodiploma.mystory2.view.upload

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mystory2.data.database.UploadRepository
import java.io.File

class UploadStoryViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadStory(file: File, description: String) = repository.uploadStory(file, description)
}