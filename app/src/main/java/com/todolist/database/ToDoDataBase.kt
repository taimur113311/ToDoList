package com.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = arrayOf(ListEntity::class, TaskEntity::class),
    version = 1,
    exportSchema = false
)

abstract class ToDoDataBase : RoomDatabase() {
    abstract fun getToDoTask(): ToDoTaskDAO

    companion object {

        @Volatile
        private lateinit var INSTANCE: ToDoDataBase

        fun getDataseClient(context: Context): ToDoDataBase {

            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, ToDoDataBase::class.java, "ToDoList_Database")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE

            }
        }

    }
}