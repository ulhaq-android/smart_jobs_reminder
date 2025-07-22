package com.example.smartjobreminder.data.manager


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.smartjobreminder.domain.repos.JobRepository

class BootReSchedulerWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: JobRepository,
    private val reminderScheduler: ReminderScheduler
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val cutoff = System.currentTimeMillis() + 24 * 60 * 60 * 1000L
        val jobs = repository.getJobsWithFutureReminders(cutoff)
        jobs.forEach { reminderScheduler.schedule(it) }
        return Result.success()
    }

}
