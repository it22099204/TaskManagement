package com.example.taskmanagement

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.databinding.TaskItemCellBinding

class TaskItemAdapter(
    private val context: Context,
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
            showDeleteConfirmationDialog(taskItems[position])
        }
    }

    private fun showDeleteConfirmationDialog(taskItem: TaskItem) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setMessage("Are you sure you want to delete this task?")
            setPositiveButton("OK") { dialog, _ ->
                clickListener.deleteTaskItem(taskItem)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun getItemCount(): Int = taskItems.size
}