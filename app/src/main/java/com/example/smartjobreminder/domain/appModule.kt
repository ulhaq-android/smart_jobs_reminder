package com.example.smartjobreminder.domain

import android.app.Application
import androidx.room.Room
import com.example.smartjobreminder.data.viewmodels.JobViewModel
import com.example.smartjobreminder.data.database.AppDatabase
import com.example.smartjobreminder.data.manager.BootReSchedulerWorker
import com.example.smartjobreminder.data.manager.ReminderScheduler
import com.example.smartjobreminder.domain.repos.JobRepository
import com.example.smartjobreminder.domain.repos.JobRepositoryImpl
import com.example.smartjobreminder.presentation.utils.SeedDataStore
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            "job_db"
        ).fallbackToDestructiveMigration(false).build()
    }
    single { get<AppDatabase>().jobDao() }
    single<JobRepository> { JobRepositoryImpl(get()) }
    single { SeedDataStore(get()) }
    single { JobViewModel(get(), get()) }
    single { ReminderScheduler(get()) }
    factory { BootReSchedulerWorker(get(), get(), get(), get()) }

}
