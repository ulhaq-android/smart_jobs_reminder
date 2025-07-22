package com.example.smartjobreminder.data.manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.smartjobreminder.data.models.Job

class ReminderScheduler(private val context: Context) {

    @SuppressLint("ScheduleExactAlarm")
    fun schedule(job: Job) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("job_title", job.title)
            putExtra("company", job.company)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            job.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val reminderTime = job.deadline - 24 * 60 * 60 * 1000L // 24 hrs before

        if (reminderTime > System.currentTimeMillis()) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }
    }

    fun cancel(job: Job) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            job.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
