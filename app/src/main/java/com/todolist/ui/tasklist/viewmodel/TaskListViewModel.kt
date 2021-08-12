package com.todolist.ui.tasklist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todolist.baseapp.BaseViewModel
import com.todolist.database.PendingCompletedList
import com.todolist.database.TaskEntity

class TaskListViewModel(private val pendingCompletedList: PendingCompletedList?) : BaseViewModel() {
    lateinit var taskItemsOfList: LiveData<List<TaskEntity>>
    lateinit var taskItems: List<TaskEntity>

    fun getTaskItemsOfList() {
        taskItemsOfList =
            taskRepository.getSpecificTaskOfList(pendingCompletedList?.listId)
    }

    fun updateTaskData(taskId: Int?, state: Boolean) {
        taskRepository.updateTaskStatus(taskId, state)
    }

    fun deleteDataFromDB(taskId: Int?) {
        taskRepository.deleteSpecificTask(taskId)
    }

    class Factory(private val pendingCompletedList: PendingCompletedList?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskListViewModel(pendingCompletedList) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}