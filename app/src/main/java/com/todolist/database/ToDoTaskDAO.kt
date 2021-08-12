package com.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoTaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListData(listEntity: ListEntity)

    @Query("SELECT * FROM ListEntity")
    fun getAllList(): LiveData<List<ListEntity>>

    @Query("SELECT * FROM TaskEntity")
    fun getAllTaskList(): LiveData<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskData(taskEntity: TaskEntity)

    @Query("Select DISTINCT L.title,L.listId,T.isCompleted from TaskEntity T INNER JOIN ListEntity L on T.id_fk_List=L.listID where T.isCompleted = 0 UNION Select title,listId,'' from ListEntity where listId not in(Select id_fk_List from TaskEntity)")
    fun getPendingTODOList(): LiveData<MutableList<PendingCompletedList>>

    @Query("SELECT * FROM TaskEntity where id_fk_List=:listId Order By dateTime Desc")
    fun getSpecificTaskOfList(listId: Int?): LiveData<List<TaskEntity>>

    @Query("Update TaskEntity SET isCompleted=:state where taskId=:taskId")
    suspend fun updateTaskStatus(taskId: Int?, state: Boolean)

    @Query("Select title,listId,1 as isCompleted from ListEntity where listId not in( Select DISTINCT L.listId from TaskEntity T INNER JOIN ListEntity L on T.id_fk_List=L.listID where T.isCompleted = 0 UNION Select listId from ListEntity where listId not in(Select id_fk_List from TaskEntity))")
    fun getCompletedToDOList(): LiveData<MutableList<PendingCompletedList>>

    @Query("Delete from TaskEntity where taskId=:taskId")
    suspend fun deleteSpecificTask(taskId: Int?)

    @Query("Delete from ListEntity where listId=:listId")
    suspend fun deleteListAndSubTask(listId: Int?)

    @Query("Update ListEntity SET title=:title where listId=:listId")
    suspend fun updateSpecificListData(listId: Int?, title: String)

    @Update
    suspend fun updateSpecificTaskData(taskEntity: TaskEntity)
}