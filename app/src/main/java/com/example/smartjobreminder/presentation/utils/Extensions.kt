package com.example.smartjobreminder.presentation.utils

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    sdf.format(Date(this))
    return sdf.format(Date(this))
}
fun AlarmManager.canScheduleExactAlarmsCompat(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        canScheduleExactAlarms()
    } else true
}

fun Context.openExactAlarmSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
    }
}

fun Context.openAppNotificationSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(intent)
    }
}

fun Context.openSystemAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = android.net.Uri.parse("package:$packageName")
    startActivity(intent)
}