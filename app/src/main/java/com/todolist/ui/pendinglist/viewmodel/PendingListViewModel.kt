package com.todolist.ui.pendinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todolist.baseapp.BaseViewModel
import com.todolist.database.PendingCompletedList

class PendingListViewModel() : BaseViewModel() {
    lateinit var pendingToDoList: LiveData<MutableList<PendingCompletedList>>
    lateinit var pendingList: MutableList<PendingCompletedList>

    fun fetchData() {
        pendingToDoList = listRepository.getPendingTODOList()
    }

    fun deleteDataFromDB(listId: Int?) {
        listRepository.deleteSpecificList(listId)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PendingListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PendingListViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}