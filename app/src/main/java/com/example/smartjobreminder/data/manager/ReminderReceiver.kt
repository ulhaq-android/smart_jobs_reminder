package com.example.smartjobreminder.data.manager


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.smartjobreminder.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val jobTitle = intent.getStringExtra("job_title") ?: "Job"
        val company = intent.getStringExtra("company") ?: ""

        val channelId = "job_reminders"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.settings)
            .setContentTitle("Job Reminder")
            .setContentText("24 hours left: $jobTitle at $company")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Job Reminders", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        manager.notify((System.currentTimeMillis() % 100000).toInt(), notification)
    }
}
