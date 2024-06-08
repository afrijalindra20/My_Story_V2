package com.dicoding.picodiploma.mystory2.data.database


import com.dicoding.picodiploma.mystory2.data.api.respone.ErrorResponse
import com.dicoding.picodiploma.mystory2.data.api.respone.RegisterResponse
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterRepository(private val apiService: ApiService) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        try {
            val response = apiService.register(name, email, password)
            return response
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            throw Exception(errorBody.message)
        }
    }
}