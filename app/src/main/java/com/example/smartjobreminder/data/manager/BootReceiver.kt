package com.example.smartjobreminder.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            val workRequest = OneTimeWorkRequestBuilder<BootReSchedulerWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
