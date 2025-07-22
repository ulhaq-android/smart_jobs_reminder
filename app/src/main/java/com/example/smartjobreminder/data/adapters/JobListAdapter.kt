package com.example.smartjobreminder.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjobreminder.R
import com.example.smartjobreminder.data.models.Job
import com.example.smartjobreminder.databinding.JobItemBinding
import com.example.smartjobreminder.presentation.utils.formatDate

class JobListAdapter : ListAdapter<Job, JobListAdapter.JobViewHolder>(DiffCallback()) {

    inner class JobViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(job: Job) {
            binding.apply {
                tvJobTitle.text = job.title
                tvCompany.text = job.company
                tvDeadline.text =
                    itemView.context.getString(R.string.deadline, job.deadline.formatDate())
                tvStatus.text = job.status
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean =
            oldItem == newItem
    }
}
