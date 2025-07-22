package com.example.smartjobreminder.presentation.activities

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartjobreminder.R
import com.example.smartjobreminder.data.viewmodels.JobViewModel
import com.example.smartjobreminder.data.adapters.JobListAdapter
import com.example.smartjobreminder.databinding.ActivityMainBinding
import com.example.smartjobreminder.presentation.utils.canScheduleExactAlarmsCompat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val jobViewmodel: JobViewModel by inject()
    private var adapter: JobListAdapter? = null
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupToolbar()
        setupData()
        clickListeners()
    }

    override fun onResume() {
        super.onResume()
        checkAndShowPermissionAlert()
    }

    private fun setupToolbar() {
        binding.headerLayout.apply {
            title.text = getString(R.string.app_name)
            settings.visibility = android.view.View.GONE
            filter.visibility = android.view.View.GONE
        }
    }

    private fun clickListeners() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddJobActivity::class.java))
        }
        binding.btnGrantPermissions.setOnClickListener {
            startActivity(Intent(this, ReminderPermissionsActivity::class.java))
        }
    }

    private fun checkAndShowPermissionAlert() {
        val missing = isPermissionMissing()
        binding.permissionAlertCard.visibility =
            if (missing) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun isPermissionMissing(): Boolean {
        val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true

        val alarmManager = getSystemService(AlarmManager::class.java)
        val alarmGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarmsCompat()
        } else true
        return !(notificationGranted && alarmGranted)
    }

    private fun setupData() {
        adapter = JobListAdapter()
        binding.jobRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.jobRecyclerView.adapter = adapter

        lifecycleScope.launch {
            jobViewmodel.loadJobs()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                jobViewmodel.jobList.collectLatest {
                    adapter?.submitList(it)
                }
            }
        }
    }
}