package com.example.taskmanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanagement.TaskItem

@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskItemDatabase : RoomDatabase(){

    abstract fun taskItemDao(): TaskItemDao

    companion object{

        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        fun getDatabase(context: Context): TaskItemDatabase{   //singleton instance  using a synchronized to have thread safety
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "task_item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}