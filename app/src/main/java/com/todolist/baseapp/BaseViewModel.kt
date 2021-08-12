package com.todolist.baseapp

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val listRepository = MyApplication.getInstance().getListRepository()
    val taskRepository = MyApplication.getInstance().getTaskRepository()

}