package com.dicoding.picodiploma.mystory2.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.mystory2.data.pref.UserPreference
import com.dicoding.picodiploma.mystory2.view.ViewModelFactory
import com.dicoding.picodiploma.mystory2.view.main.MainActivity
import com.dicoding.picodiploma.mystory2.view.main.dataStore
import com.dicoding.picodiploma.mystory2.data.database.UserRepository
import com.dicoding.picodiploma.mystory2.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.mystory2.di.Injection
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = Injection.provideUserRepository(this)
        userPreference = UserPreference.getInstance(dataStore)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && password.length >= 8) {
                viewModel.login(email, password) { token ->
                    lifecycleScope.launch {
                        userPreference.saveSession(email, token, true)
                        navigateToMainActivity()
                    }
                }
            } else {
                if (email.isEmpty()) {
                    binding.edLoginEmail.error = "Email cannot be empty"
                }
                if (password.isEmpty()) {
                    binding.edLoginPassword.error = "Password cannot be empty"
                } else if (password.length < 8) {
                    binding.edLoginPassword.error = "Password must be at least 8 characters"
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val animatorSet = AnimatorSet().apply {
            playSequentially(
                ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100),
                ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
            )
            startDelay = 100
        }
        animatorSet.start()
    }
}