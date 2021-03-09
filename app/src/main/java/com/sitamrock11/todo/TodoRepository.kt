package com.sitamrock11.todo

import androidx.lifecycle.LiveData
import com.sitamrock11.todo.Room.Todo
import com.sitamrock11.todo.Room.TodoDao

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun insertTask(task: Todo){
        todoDao.insertTask(task)
    }
   suspend fun deleteTask(uid:Long){
        todoDao.deleteTask(uid)
    }
    fun getAllTask():LiveData<List<Todo>>{
      return  todoDao.getAllTask()
    }
   suspend fun finishTask(uid:Long){
        todoDao.finishTask(uid)
    }
}