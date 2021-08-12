package com.todolist.ui.tasklist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todolist.R
import com.todolist.baseapp.BaseViewModel
import com.todolist.database.TaskEntity
import com.todolist.utils.SingleEvent
import java.util.*

class DialogAddTaskViewModel(
    private val listId: Int?,
    val isEdit: Boolean?,
    dataItem: TaskEntity?
) : BaseViewModel() {

    val dismissDialog = MutableLiveData<SingleEvent<Unit>>()
    val etListTitle = MutableLiveData("")
    val etListContent = MutableLiveData("")
    val error = MutableLiveData("")
    val callAPI = MutableLiveData<SingleEvent<Unit>>()
    val title = ObservableInt()
    val buttonText = ObservableInt()
    val item = dataItem;

    init {

        if (isEdit == true) {
            title.set(R.string.edit_a_task)
            buttonText.set(R.string.edit)
            etListTitle.value = dataItem?.title
            etListContent.value = dataItem?.content
        } else {
            title.set(R.string.add_new_task)
            buttonText.set(R.string.Add)
        }
    }

    fun onDialogContinueClicked(view: View) {

        if (etListTitle.value.toString().isEmpty()) {
            error.value = "Enter Valid Task Title"
        } else if (etListContent.value.toString().isEmpty()) {
            error.value = "Enter Valid Task Content"
        } else {
            error.value = ""
            callAPI.value = SingleEvent(Unit)
        }

    }

    fun onDialogDismissClicked() {
        dismissDialog.value = SingleEvent(Unit)
    }

    fun addNewList() {
        val currentTime: Date = Calendar.getInstance().getTime()

        taskRepository.insertData(
            TaskEntity(
                etListTitle.value.toString(),
                etListContent.value.toString(),
                currentTime.toString(), listId, false
            )
        )
        onDialogDismissClicked()
    }

    fun updateTask() {

        taskRepository.updateSpecificTask(
            TaskEntity(
                etListTitle.value.toString(),
                etListContent.value.toString(),
                item?.dateTime, item?.id_fk_List, item?.isCompleted
            ).apply {
                taskId = item?.taskId
            }
        )
        onDialogDismissClicked()
    }


    class Factory(private val listId: Int?, val isEdit: Boolean?, val dataItem: TaskEntity?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DialogAddTaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DialogAddTaskViewModel(listId, isEdit, dataItem) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}