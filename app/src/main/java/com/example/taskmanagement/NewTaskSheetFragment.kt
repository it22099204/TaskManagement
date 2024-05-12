package com.example.taskmanagement

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanagement.databinding.FragmentNewTaskSheetBinding
import com.example.taskmanagement.viewModel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheetFragment (private var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(taskItem != null){
            binding.taskTitle.text = getString(R.string.edit_task)    //changing the title
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else{
            binding.taskTitle.text = getString(R.string.new_task)
        }

        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.btnSave.setOnClickListener {
            saveAction()
        }
        binding.btnTimePicker.setOnClickListener {
            openTimePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val lister = TimePickerDialog.OnTimeSetListener{_,selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour,selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity,lister,dueTime!!.hour, dueTime!!.minute,true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.btnTimePicker.text = String.format("%02d:%02d", dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction() {
        val name = binding.name.text.toString()
        val description = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null)null else TaskItem.timeFormatter.format(dueTime)

        if(taskItem == null){
            val newTask = TaskItem(name,description,dueTimeString,null)
            taskViewModel.addTaskItem(newTask)
        }
        else{
            taskItem!!.name = name
            taskItem!!.desc = description
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

}