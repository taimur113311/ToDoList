package com.todolist.utils

import android.content.Context
import android.view.View
import android.widget.Toast

object UtilExtensions {
    fun View.isVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun Context.myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}