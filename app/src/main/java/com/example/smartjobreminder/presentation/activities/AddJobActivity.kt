package com.example.smartjobreminder.presentation.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartjobreminder.R
import com.example.smartjobreminder.data.models.Job
import com.example.smartjobreminder.data.viewmodels.JobViewModel
import com.example.smartjobreminder.data.manager.ReminderScheduler
import com.example.smartjobreminder.databinding.ActivityAddJobBinding
import com.example.smartjobreminder.presentation.utils.formatDate
import org.koin.android.ext.android.inject
import java.util.Calendar

class AddJobActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddJobBinding
    private val viewModel: JobViewModel by inject()
    private val reminderScheduler: ReminderScheduler by inject()
    private var filterList: List<Job> = emptyList()

    private var selectedDeadlineMillis: Long = getDefaultDeadline()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupToolbar()
        setupForm()
        binding.btnSubmitJob.setOnClickListener { validateAndSubmit() }
    }

    private fun setupToolbar() {
        binding.headerLayout.apply {
            title.text = getString(R.string.add_job)
            settings.visibility = View.GONE
            filter.visibility = View.GONE
        }
    }

    private fun setupForm() {
        // Status dropdown
        ArrayAdapter.createFromResource(
            this,
            R.array.job_status,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerStatus.adapter = it
        }

        // Set default date
        binding.tvDeadline.text = selectedDeadlineMillis.formatDate()
        binding.tvDeadline.setOnClickListener { openDatePicker() }
    }

    private fun openDatePicker() {
        val cal = Calendar.getInstance().apply {
            timeInMillis = selectedDeadlineMillis
        }
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val picked = Calendar.getInstance().apply {
                    set(year, month, day, 23, 59)
                }
                selectedDeadlineMillis = picked.timeInMillis
                binding.tvDeadline.text = selectedDeadlineMillis.formatDate()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun validateAndSubmit() {
        val title = binding.etTitle.text.toString().trim()
        val company = binding.etCompany.text.toString().trim()
        val status = binding.spinnerStatus.selectedItem.toString()

        when {
            title.isEmpty() -> {
                binding.etTitle.error = "Enter job title"
            }

            company.isEmpty() -> {
                binding.etCompany.error = "Enter company name"
            }

            else -> {
                val job = Job(
                    title = title,
                    company = company,
                    deadline = selectedDeadlineMillis,
                    status = status
                )
                viewModel.insertJob(job)
                reminderScheduler.schedule(job)
                Toast.makeText(applicationContext, "Job added!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun getDefaultDeadline(): Long {
        return System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L
    }
}