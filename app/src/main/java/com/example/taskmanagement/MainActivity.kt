package com.example.taskmanagement

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.database.TodoApplication
import com.example.taskmanagement.databinding.ActivityMainBinding
import com.example.taskmanagement.viewModel.TaskItemModelFactory
import com.example.taskmanagement.viewModel.TaskViewModel

class MainActivity : AppCompatActivity(), TaskItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewTask.setOnClickListener{
            NewTaskSheetFragment(null).show(supportFragmentManager,"newTaskTag")
        }
        setRecyclerView()
        setupSearchView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager= LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it,mainActivity)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { query ->
                        search(query)
                    }
                    return true
                }
            }
        )
    }

    private fun search(query: String) {
        val filteredList = taskViewModel.taskItems.value?.filter { taskItem ->
            taskItem.name.contains(query, true)
        }
        filteredList?.let {
            (binding.todoListRecyclerView.adapter as TaskItemAdapter).taskItems = it
            binding.todoListRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheetFragment(taskItem).show(supportFragmentManager,"newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }
}