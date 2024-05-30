package com.example.dailyactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyactivity.databinding.ItemTaskBinding
import com.example.dailyactivity.entity.Task

class HomeAdapter(private var taskList: List<Task>) : RecyclerView.Adapter<HomeAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.taskTitle.text = task.title
        holder.binding.taskDescription.text = task.description
        holder.binding.taskTime.text = "${task.startTime} ~ ${task.endTime}"
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}
