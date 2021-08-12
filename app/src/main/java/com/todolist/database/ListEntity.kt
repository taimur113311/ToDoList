package com.todolist.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ListEntity(
    var title: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var listId: Int? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = ListEntity::class,
        parentColumns = arrayOf("listId"),
        childColumns = arrayOf("id_fk_List"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class TaskEntity(
    var title: String?,
    var content:String,
    var dateTime:String?,
    var id_fk_List: Int?,
    var isCompleted: Boolean?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var taskId: Int? = null
}

@Parcelize
data class PendingCompletedList(
    var title: String?,
    var listId: Int?,
    var isCompleted: Boolean
) : Parcelable


