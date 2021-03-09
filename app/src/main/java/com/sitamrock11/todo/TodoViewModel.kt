package com.sitamrock11.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sitamrock11.todo.Room.Todo
import com.sitamrock11.todo.Room.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) :AndroidViewModel(application) {
    val allTodos:LiveData<List<Todo>>
    val repo:TodoRepository
    init {
        val dao=TodoDatabase.getDatabase(application).todoDao()
         repo=TodoRepository(dao)
         allTodos=repo.getAllTask()
    }
    fun deleteTask(uid:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTask(uid)
        }
    }

}