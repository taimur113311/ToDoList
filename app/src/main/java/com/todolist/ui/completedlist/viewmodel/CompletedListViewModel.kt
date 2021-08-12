package com.todolist.ui.completedlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todolist.baseapp.BaseViewModel
import com.todolist.database.PendingCompletedList

class CompletedListViewModel : BaseViewModel() {
    lateinit var completedToDoList: LiveData<MutableList<PendingCompletedList>>
    lateinit var completedList: MutableList<PendingCompletedList>

    fun fetchData() {
        completedToDoList = listRepository.getCompleteToDoList()
    }

    fun deleteDataFromDB(listId: Int?) {
        listRepository.deleteSpecificList(listId)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CompletedListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CompletedListViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}