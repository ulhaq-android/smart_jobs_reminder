package com.example.smartjobreminder.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartjobreminder.data.models.Job

@Database(entities = [Job::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
}
