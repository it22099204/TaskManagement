package com.example.taskmanagement.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagement.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao {
    @Query("SELECT * FROM to_do_list_table ORDER BY id DESC")
    fun allTaskItems(): Flow<List<TaskItem>>

    //search
    @Query("SELECT * FROM to_do_list_table WHERE name LIKE :query ORDER BY id DESC")
    fun searchTaskItems(query: String): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: TaskItem)

    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)

    @Delete
    suspend fun deleteTaskItem(taskItem: TaskItem)
}