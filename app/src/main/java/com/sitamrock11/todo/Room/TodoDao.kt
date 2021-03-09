package com.sitamrock11.todo.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task:Todo)
    @Query("Delete from Todo_Table where id=:uid")
    suspend fun deleteTask(uid:Long)
    @Query("Select * from Todo_Table where isFinished=-1")
    fun getAllTask():LiveData<List<Todo>>
    @Query("update todo_table set isFinished=1 where id=:uid")
    suspend fun finishTask(uid:Long)
}