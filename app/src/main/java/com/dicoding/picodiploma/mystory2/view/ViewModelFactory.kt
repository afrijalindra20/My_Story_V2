package com.dicoding.picodiploma.mystory2.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mystory2.data.database.LoginRepository
import com.dicoding.picodiploma.mystory2.data.database.RegisterRepository
import com.dicoding.picodiploma.mystory2.data.database.StoryRepository
import com.dicoding.picodiploma.mystory2.data.database.UploadRepository
import com.dicoding.picodiploma.mystory2.data.database.UserRepository
import com.dicoding.picodiploma.mystory2.di.Injection
import com.dicoding.picodiploma.mystory2.view.detail.DetailViewModel
import com.dicoding.picodiploma.mystory2.view.login.LoginViewModel
import com.dicoding.picodiploma.mystory2.view.main.MainViewModel
import com.dicoding.picodiploma.mystory2.view.maps.MapsViewModel
import com.dicoding.picodiploma.mystory2.view.signup.SignupViewModel
import com.dicoding.picodiploma.mystory2.view.upload.UploadStoryViewModel

class ViewModelFactory(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
    private val registerRepository: RegisterRepository,
    private val loginRepository: LoginRepository,
    private val uploadRepository: UploadRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(loginRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(registerRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> {
                UploadStoryViewModel(uploadRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val database = Injection.provideDatabase(context)
                val storyRepository = Injection.provideRepository(context, database)
                val userRepository = Injection.provideUserRepository(context)
                val registerRepository = Injection.provideRegisterRepository(context)
                val loginRepository = Injection.provideLoginRepository(context)
                val uploadRepository = Injection.provideUploadSRepository(context)

                val instance = ViewModelFactory(
                    storyRepository,
                    userRepository,
                    registerRepository,
                    loginRepository,
                    uploadRepository
                )
                INSTANCE = instance
                instance
            }
        }
    }
    }