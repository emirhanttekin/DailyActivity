package com.example.dailyactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyactivity.databinding.ItemTaskBinding
import com.example.dailyactivity.databinding.ItemfragmentTaskBinding
import com.example.dailyactivity.entity.Task

class HomeAdapter(private var taskList: List<Task>, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // ViewHolder classes
    class NormalViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
    class AlternateViewHolder(val binding: ItemfragmentTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NormalViewHolder(binding)
            }
            VIEW_TYPE_ALTERNATE -> {
                val binding = ItemfragmentTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AlternateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = taskList[position]
        when (holder.itemViewType) {
            VIEW_TYPE_NORMAL -> {
                val normalViewHolder = holder as NormalViewHolder
                normalViewHolder.binding.taskTitle.text = task.title
                normalViewHolder.binding.taskDescription.text = task.description
                normalViewHolder.binding.taskTime.text = "${task.startTime} ~ ${task.endTime}"
            }
            VIEW_TYPE_ALTERNATE -> {
                val alternateViewHolder = holder as AlternateViewHolder
                alternateViewHolder.binding.taskTitles.text = task.title
                alternateViewHolder.binding.taskDescriptionn.text = task.description
                alternateViewHolder.binding.taskTimes.text = "${task.startTime} ~ ${task.endTime}"
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun getItemViewType(position: Int): Int {
        // Return the viewType passed to the adapter
        return viewType
    }

    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_ALTERNATE = 2
    }
}
