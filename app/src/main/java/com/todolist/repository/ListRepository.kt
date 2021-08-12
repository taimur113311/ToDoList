package com.todolist.repository

import androidx.lifecycle.LiveData
import com.todolist.baseapp.MyApplication
import com.todolist.database.ListEntity
import com.todolist.database.PendingCompletedList
import com.todolist.database.ToDoDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ListRepository {
    var toDoDataBase: ToDoDataBase? = null

    fun insertData(listEntity: ListEntity) {
        toDoDataBase = MyApplication.getDataBase()

        CoroutineScope(IO).launch {
            toDoDataBase?.getToDoTask()?.insertListData(listEntity)
        }
    }

    fun getPendingTODOList(): LiveData<MutableList<PendingCompletedList>> {
        toDoDataBase = MyApplication.getDataBase()
        return toDoDataBase?.getToDoTask()?.getPendingTODOList()!!
    }

    fun getCompleteToDoList(): LiveData<MutableList<PendingCompletedList>> {
        toDoDataBase = MyApplication.getDataBase()
        return toDoDataBase?.getToDoTask()?.getCompletedToDOList()!!
    }

    fun deleteSpecificList(listId: Int?) {
        toDoDataBase = MyApplication.getDataBase()
        CoroutineScope(IO).launch {
            toDoDataBase?.getToDoTask()?.deleteListAndSubTask(listId)
        }
    }

    fun updateListData(listId: Int?, title: String) {
        toDoDataBase = MyApplication.getDataBase()
        CoroutineScope(IO).launch {
            toDoDataBase?.getToDoTask()?.updateSpecificListData(listId, title)
        }
    }
}