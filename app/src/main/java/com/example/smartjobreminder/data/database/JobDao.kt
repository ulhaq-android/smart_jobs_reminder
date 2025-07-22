package com.example.smartjobreminder.data.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smartjobreminder.data.models.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jobs: List<Job>)
    @Update
    suspend fun updateJob(job: Job)

    @Delete
    suspend fun deleteJob(job: Job)

    @Query("SELECT * FROM jobs ORDER BY deadline ASC")
    fun getAllJobs(): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE status = :status ORDER BY deadline ASC")
    fun getJobsByStatus(status: String): Flow<List<Job>>

    @Query("SELECT * FROM jobs WHERE deadline > :minTriggerTime")
    suspend fun getJobsWithFutureReminders(minTriggerTime: Long): List<Job>

}

