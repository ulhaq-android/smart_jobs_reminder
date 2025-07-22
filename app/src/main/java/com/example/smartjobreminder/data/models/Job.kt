package com.example.smartjobreminder.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val company: String,
    val deadline: Long, // timestamp in millis
    val status: String // "Upcoming", "Applied", "Bookmarked"
)

