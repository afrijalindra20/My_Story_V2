package com.dicoding.picodiploma.mystory2.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.mystory2.data.database.LoginRepository
import com.dicoding.picodiploma.mystory2.data.database.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun login(email: String, password: String, onSuccess: (token: String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (!response.error!!) {
                    val loginResult = response.loginResult
                    if (loginResult != null) {
                        val token = loginResult.token ?: ""
                        onSuccess(token)
                    }
                } else {

                }
            } catch (e: Exception) {
            }
        }
    }
}