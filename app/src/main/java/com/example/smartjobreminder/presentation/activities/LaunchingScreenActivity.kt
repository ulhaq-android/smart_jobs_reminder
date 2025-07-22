package com.example.smartjobreminder.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.smartjobreminder.R
import com.example.smartjobreminder.data.viewmodels.JobViewModel
import com.example.smartjobreminder.databinding.ActivityLaunchingScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LaunchingScreenActivity : AppCompatActivity() {
    private val binding: ActivityLaunchingScreenBinding by lazy {
        ActivityLaunchingScreenBinding.inflate(layoutInflater)
    }
    private val viewModel: JobViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            viewModel.maybeSeedJobs()
            delay(3000)
            startActivity(Intent(this@LaunchingScreenActivity, MainActivity::class.java))
            finish()
        }

    }
}