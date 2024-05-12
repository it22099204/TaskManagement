package com.example.taskmanagement

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
   private val context: Context,
   internal val binding: TaskItemCellBinding,
   private val  clickListener: TaskItemClickListener,
   private val taskItems: List<TaskItem>
): RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem){
        binding.name.text = taskItem.name

        if(taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.btnComplete.setImageResource(taskItem.imageResource())
        binding.btnComplete.setColorFilter(taskItem.imageColor(context))

        binding.btnComplete.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }

        if(taskItem.dueTime() != null){
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        }
        else{
            binding.dueTime.text = ""
        }
    }

    //delete part
    init {
        binding.btnDelete.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.deleteTaskItem(taskItems[position])
            }
        }
    }
}