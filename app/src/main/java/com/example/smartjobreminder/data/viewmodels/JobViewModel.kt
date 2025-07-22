package com.example.smartjobreminder.data.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartjobreminder.data.models.Job
import com.example.smartjobreminder.domain.repos.JobRepository
import com.example.smartjobreminder.presentation.utils.SeedDataStore
import com.example.smartjobreminder.presentation.utils.jobs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JobViewModel(
    private val repository: JobRepository,
    private val seedDataStore: SeedDataStore
) : ViewModel() {

    private val _jobList = MutableStateFlow<List<Job>>(emptyList())
    val jobList: StateFlow<List<Job>> = _jobList.asStateFlow()


    suspend fun maybeSeedJobs() {
        if (!seedDataStore.isSeeded()) {
            repository.insertAll(jobs)
            seedDataStore.setSeeded(true)
        }
    }

    fun loadJobs() {
        viewModelScope.launch {
            repository.getAllJobs().collectLatest {
                _jobList.value = it
            }
        }
    }

    fun insertJob(job: Job) = viewModelScope.launch {
        repository.insert(job)
    }

    fun deleteJob(job: Job) = viewModelScope.launch {
        repository.delete(job)
    }

    fun updateJob(job: Job) = viewModelScope.launch {
        repository.update(job)
    }

    fun filterByStatus(status: String): List<Job> {
        return jobList.value.filter { it.status == status }
    }
}
