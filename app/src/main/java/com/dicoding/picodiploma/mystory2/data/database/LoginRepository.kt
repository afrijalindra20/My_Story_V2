package com.dicoding.picodiploma.mystory2.data.database


import com.dicoding.picodiploma.mystory2.data.api.respone.ErrorResponse
import com.dicoding.picodiploma.mystory2.data.api.respone.LoginResponse
import com.dicoding.picodiploma.mystory2.data.api.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        try {
            val response = apiService.login(email, password)
            return response
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            throw Exception(errorBody.message)
        }
    }
}