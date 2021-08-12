package com.todolist.repository

import androidx.lifecycle.LiveData
import com.todolist.baseapp.MyApplication
import com.todolist.database.TaskEntity
import com.todolist.database.ToDoDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskRepository {
    var toDoDataBase: ToDoDataBase? = null

    fun insertData(taskEntity: TaskEntity) {
        toDoDataBase = MyApplication.getDataBase()

        CoroutineScope(IO).launch {
            toDoDataBase!!.getToDoTask().insertTaskData(taskEntity)
        }
    }

    fun getSpecificTaskOfList(listId: Int?): LiveData<List<TaskEntity>> {
        toDoDataBase = MyApplication.getDataBase()
        return toDoDataBase!!.getToDoTask().getSpecificTaskOfList(listId)
    }

    fun updateTaskStatus(taskId: Int?, state: Boolean) {
        toDoDataBase = MyApplication.getDataBase()

        CoroutineScope(IO).launch {
            toDoDataBase!!.getToDoTask().updateTaskStatus(taskId, state)
        }
    }

    fun deleteSpecificTask(taskId: Int?) {
        toDoDataBase = MyApplication.getDataBase()

        CoroutineScope(IO).launch {
            toDoDataBase!!.getToDoTask().deleteSpecificTask(taskId)
        }
    }

    fun updateSpecificTask(taskEntity: TaskEntity) {
        toDoDataBase = MyApplication.getDataBase()

        CoroutineScope(IO).launch {
            toDoDataBase!!.getToDoTask().updateSpecificTaskData(taskEntity)
        }    }

}