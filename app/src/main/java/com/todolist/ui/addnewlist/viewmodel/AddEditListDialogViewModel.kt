package com.todolist.ui.addnewlist.viewmodel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.todolist.R
import com.todolist.baseapp.BaseViewModel
import com.todolist.database.ListEntity
import com.todolist.database.PendingCompletedList
import com.todolist.utils.SingleEvent

class AddEditListDialogViewModel(public val isEdit: Boolean?, item: PendingCompletedList?) :
    BaseViewModel() {
    val dismissDialog = MutableLiveData<SingleEvent<Unit>>()
    val etListTitle = MutableLiveData("")
    val error = MutableLiveData("")
    val title = ObservableInt()
    val buttonText = ObservableInt()
    val callAPI = MutableLiveData<SingleEvent<Unit>>()

    init {
        if (isEdit == true) {
            title.set(R.string.edit_a_list)
            buttonText.set(R.string.edit_list)
            etListTitle.value = item?.title
        } else {
            title.set(R.string.add_new_list)
            buttonText.set(R.string.add_list)
        }
    }

    fun onDialogContinueClicked(view: View) {

        if (etListTitle.value.toString().isEmpty()) {
            error.value = "Enter Valid List Title"
        } else {
            error.value = ""
            callAPI.value = SingleEvent(Unit)
        }
    }

    fun onDialogDismissClicked() {
        dismissDialog.value = SingleEvent(Unit)
    }

    fun addNewList() {
        listRepository.insertData(ListEntity(title = etListTitle.value.toString()))
        onDialogDismissClicked()
    }

    fun updateList(item: PendingCompletedList?) {
        listRepository.updateListData(item?.listId, etListTitle.value.toString())
        onDialogDismissClicked()
    }

    class Factory(val isEdit: Boolean?, val dataItem: PendingCompletedList?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddEditListDialogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddEditListDialogViewModel(isEdit, dataItem) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}