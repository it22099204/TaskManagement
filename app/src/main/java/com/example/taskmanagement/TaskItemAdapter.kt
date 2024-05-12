package com.example.taskmanagement

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.databinding.TaskItemCellBinding

class TaskItemAdapter(
    var taskItems: List<TaskItem>,
    private val  clickListener: TaskItemClickListener

): RecyclerView.Adapter<TaskItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from,parent,  false)
        return TaskItemViewHolder(parent.context,binding,clickListener,taskItems)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
        holder.binding.btnDelete.setOnClickListener {
            clickListener.deleteTaskItem(taskItems[position])
        }
    }

    override fun getItemCount(): Int = taskItems.size
}