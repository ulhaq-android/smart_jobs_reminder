package com.example.smartjobreminder.domain.repos


import com.example.smartjobreminder.data.models.Job
import com.example.smartjobreminder.data.database.JobDao
import kotlinx.coroutines.flow.Flow

class JobRepositoryImpl(private val dao: JobDao) : JobRepository {

    override fun getAllJobs(): Flow<List<Job>> = dao.getAllJobs()

    override fun getJobsByStatus(status: String): Flow<List<Job>> = dao.getJobsByStatus(status)

    override suspend fun insert(job: Job) = dao.insertJob(job)

    override suspend fun insertAll(jobs: List<Job>) =  dao.insertAll(jobs)

    override suspend fun update(job: Job) = dao.updateJob(job)

    override suspend fun delete(job: Job) = dao.deleteJob(job)

    override suspend fun getJobsWithFutureReminders(minTriggerTime: Long): List<Job> {
        return dao.getJobsWithFutureReminders(minTriggerTime)
    }

}
