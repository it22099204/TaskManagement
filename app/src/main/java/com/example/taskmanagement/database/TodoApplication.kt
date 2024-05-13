package com.example.taskmanagement.database

import android.app.Application

class TodoApplication: Application() {
    // Lazy initialization for the TaskItemDatabase
    private val database by lazy { TaskItemDatabase.getDatabase(this) }

    // Lazy initialization for the TaskItemRepository using DAO
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
}