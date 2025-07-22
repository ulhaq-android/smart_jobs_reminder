package com.example.smartjobreminder.presentation.activities

import android.Manifest
import android.app.AlarmManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartjobreminder.R
import com.example.smartjobreminder.databinding.ActivityReminderPermissionsBinding
import com.example.smartjobreminder.presentation.utils.canScheduleExactAlarmsCompat
import com.example.smartjobreminder.presentation.utils.openExactAlarmSettings
import com.example.smartjobreminder.presentation.utils.openSystemAppSettings

class ReminderPermissionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderPermissionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReminderPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

setupToolBar()
        setupExactAlarmToggle()
        setupNotificationToggle()
        setupBootToggle()
    }

    private fun setupToolBar() {
        binding.headerLayout.apply {
            title.text = getString(R.string.permissions)
            settings.visibility = View.GONE
            filter.visibility = View.GONE
        }
    }

    private fun setupExactAlarmToggle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(AlarmManager::class.java)
            val isGranted = alarmManager.canScheduleExactAlarmsCompat()

            // Reflect current state in switch
            binding.switchExactAlarm.isChecked = isGranted

            binding.switchExactAlarm.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !isGranted) {
                    openExactAlarmSettings()
                }
            }
        } else {
            binding.switchExactAlarm.isEnabled = false
        }
    }

    private fun setupNotificationToggle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            binding.switchNotificationPermission.isChecked = isGranted

            binding.switchNotificationPermission.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !isGranted) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        2001
                    )
                }
            }
        } else {
            binding.switchNotificationPermission.isEnabled = false
        }
    }

    private fun setupBootToggle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // No direct permission to query, assume false unless user previously granted
            binding.switchBootPermission.isChecked = true
            binding.switchBootPermission.setOnCheckedChangeListener { _, _ ->
                openSystemAppSettings()
            }
        } else {
            binding.switchBootPermission.isEnabled = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2001 && grantResults.isNotEmpty()) {
            binding.switchNotificationPermission.isChecked =
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }
}