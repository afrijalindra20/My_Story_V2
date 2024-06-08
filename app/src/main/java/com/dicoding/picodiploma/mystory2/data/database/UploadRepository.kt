package com.dicoding.picodiploma.mystory2.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.mystory2.data.api.respone.FileUploadResponse
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadRepository private constructor(
    private val apiService: ApiService
) {

    fun uploadStory(imageFile: File, description: String, lat: Double? = null, lon: Double? = null): LiveData<ResultState<FileUploadResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())

            val response = apiService.uploadStory(imageMultipart, descriptionRequestBody, lat, lon)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UploadRepository? = null

        fun getInstance(apiService: ApiService): UploadRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UploadRepository(apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}