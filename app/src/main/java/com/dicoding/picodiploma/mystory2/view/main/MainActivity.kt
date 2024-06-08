package com.dicoding.picodiploma.mystory2.view.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mystory2.data.pref.UserPreference
import com.dicoding.picodiploma.mystory2.view.ViewModelFactory
import com.dicoding.picodiploma.mystory2.R
import com.dicoding.picodiploma.mystory2.databinding.ActivityMainBinding
import com.dicoding.picodiploma.mystory2.view.data.adapter.LoadingStateAdapter
import com.dicoding.picodiploma.mystory2.view.detail.DetailStoryActivity
import com.dicoding.picodiploma.mystory2.view.login.LoginActivity
import com.dicoding.picodiploma.mystory2.view.maps.MapsActivity
import com.dicoding.picodiploma.mystory2.view.upload.UploadActivity
import com.dicoding.picodiploma.mystory2.view.welcome.WelcomeActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var userPreference: UserPreference

    private val uploadStoryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                adapter.refresh()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(dataStore)

        setSupportActionBar(binding.toolbar)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            uploadStoryResultLauncher.launch(intent)
        }

        setupView()
        setupRecyclerView()
        checkUserSession()
    }

    private fun checkUserSession() {
        lifecycleScope.launch {
            val userModel = userPreference.getSession().first()
            if (userModel != null && userModel.isLogin) {
                // Tidak perlu memanggil viewModel.getStories() karena data cerita sudah diambil secara otomatis
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }

            R.id.action_maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            viewModel.logout()
            userPreference.logout()
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
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
    }

    private fun setupRecyclerView() {
        adapter = StoryAdapter { story ->
            val intent = Intent(this, DetailStoryActivity::class.java)
            intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
            startActivity(intent)
        }
        binding.storyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storyRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { adapter.retry() },
            footer = LoadingStateAdapter { adapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.stories.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }

        }
    }
}