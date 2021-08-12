package com.todolist.baseapp

import android.app.Application
import android.content.Context
import com.todolist.database.ToDoDataBase
import com.todolist.repository.ListRepository
import com.todolist.repository.TaskRepository

class MyApplication : Application() {
    companion object {
        private var instance: MyApplication? = null
        lateinit var toDoDataBase: ToDoDataBase

        fun getInstance(): MyApplication {
            synchronized(MyApplication::class.java) {
                if (instance == null) {
                    instance = MyApplication()
                }
            }
            return instance!!
        }

        fun getDataBase(): ToDoDataBase {
            return toDoDataBase
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeDB(this)
    }

    fun initializeDB(context: Context) {
        toDoDataBase = ToDoDataBase.getDataseClient(context)
    }

    private var listRepository: ListRepository? = null
    fun getListRepository(): ListRepository {
        synchronized(MyApplication::class.java) {
            if (listRepository == null)
                listRepository = ListRepository()
        }
        return listRepository!!
    }

    private var taskRepository: TaskRepository? = null
    fun getTaskRepository(): TaskRepository {
        synchronized(MyApplication::class.java) {
            if (taskRepository == null)
                taskRepository = TaskRepository()
        }
        return taskRepository!!
    }

}