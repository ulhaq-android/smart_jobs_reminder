package com.example.smartjobreminder.domain.repos

import com.example.smartjobreminder.data.models.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getAllJobs(): Flow<List<Job>>
    fun getJobsByStatus(status: String): Flow<List<Job>>
    suspend fun insert(job: Job)
    suspend fun insertAll(jobs: List<Job>)
    suspend fun update(job: Job)
    suspend fun delete(job: Job)
    suspend fun getJobsWithFutureReminders(minTriggerTime: Long): List<Job>
}
