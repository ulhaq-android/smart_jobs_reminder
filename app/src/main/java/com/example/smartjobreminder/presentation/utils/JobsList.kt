package com.example.smartjobreminder.presentation.utils

import com.example.smartjobreminder.data.models.Job

val jobs = listOf(
    Job(title = "Android Developer", company = "Google", deadline = nowPlusDays(10), status = "Upcoming"),
    Job(title = "Backend Engineer", company = "Amazon", deadline = nowPlusDays(12), status = "Upcoming"),
    Job(title = "iOS Developer", company = "Apple", deadline = nowPlusDays(15), status = "Upcoming"),
    Job(title = "Cloud Architect", company = "Microsoft", deadline = nowPlusDays(9), status = "Upcoming"),
    Job(title = "DevOps Engineer", company = "Netflix", deadline = nowPlusDays(20), status = "Upcoming"),
    Job(title = "QA Automation Engineer", company = "Meta", deadline = nowPlusDays(11), status = "Upcoming"),
    Job(title = "Full Stack Developer", company = "Adobe", deadline = nowPlusDays(17), status = "Upcoming"),
    Job(title = "Frontend Developer", company = "Spotify", deadline = nowPlusDays(14), status = "Upcoming"),
    Job(title = "Machine Learning Engineer", company = "NVIDIA", deadline = nowPlusDays(19), status = "Upcoming"),
    Job(title = "Data Scientist", company = "IBM", deadline = nowPlusDays(18), status = "Upcoming"),
    Job(title = "Security Analyst", company = "Oracle", deadline = nowPlusDays(16), status = "Upcoming"),
    Job(title = "System Engineer", company = "Dell", deadline = nowPlusDays(13), status = "Upcoming"),
    Job(title = "Network Engineer", company = "Cisco", deadline = nowPlusDays(22), status = "Upcoming"),
    Job(title = "Site Reliability Engineer", company = "Uber", deadline = nowPlusDays(21), status = "Upcoming"),
    Job(title = "Blockchain Developer", company = "Coinbase", deadline = nowPlusDays(23), status = "Upcoming"),
    Job(title = "AI Researcher", company = "OpenAI", deadline = nowPlusDays(24), status = "Upcoming"),
    Job(title = "Technical Product Manager", company = "Twitter", deadline = nowPlusDays(25), status = "Upcoming"),
    Job(title = "Penetration Tester", company = "Palo Alto Networks", deadline = nowPlusDays(26), status = "Upcoming"),
    Job(title = "Game Developer", company = "Electronic Arts", deadline = nowPlusDays(27), status = "Upcoming"),
    Job(title = "Database Administrator", company = "MongoDB", deadline = nowPlusDays(28), status = "Upcoming"),
    Job(title = "UI/UX Designer", company = "Figma", deadline = nowPlusDays(29), status = "Upcoming"),
    Job(title = "AR/VR Engineer", company = "Unity", deadline = nowPlusDays(30), status = "Upcoming"),
    Job(title = "Robotics Engineer", company = "Boston Dynamics", deadline = nowPlusDays(10), status = "Upcoming"),
    Job(title = "Embedded Systems Engineer", company = "Intel", deadline = nowPlusDays(11), status = "Upcoming"),
    Job(title = "Platform Engineer", company = "Shopify", deadline = nowPlusDays(12), status = "Upcoming"),
    Job(title = "Technical Writer", company = "Atlassian", deadline = nowPlusDays(13), status = "Upcoming"),
    Job(title = "Data Analyst", company = "LinkedIn", deadline = nowPlusDays(14), status = "Upcoming"),
    Job(title = "VR Content Engineer", company = "HTC Vive", deadline = nowPlusDays(15), status = "Upcoming"),
    Job(title = "Big Data Engineer", company = "Cloudera", deadline = nowPlusDays(16), status = "Upcoming"),
    Job(title = "Software Architect", company = "Salesforce", deadline = nowPlusDays(17), status = "Upcoming"),
)

fun nowPlusDays(days: Int): Long {
    val now = System.currentTimeMillis()
    return now + days * 24 * 60 * 60 * 1000L
}

