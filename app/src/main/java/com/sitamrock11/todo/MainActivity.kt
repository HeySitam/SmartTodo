package com.sitamrock11.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sitamrock11.todo.Room.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), IRVAdapter {
    val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TodoViewModel::class.java)
    }
     lateinit var todoList:List<Todo>
    val rvAdapter=RVAdapter(this,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  val rvAdapter=RVAdapter(this,this)
        rvTodo.apply{
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=this@MainActivity.rvAdapter
        }
        viewModel.allTodos.observe(this, Observer{
            rvAdapter.updateList(it)
        })
        setSupportActionBar(toolbar)
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, TaskAddingActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                Toast.makeText(this@MainActivity, "Clicked on history button", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDeleteBtnClicked(todo: Todo) {
           viewModel.deleteTask(todo.id)
    }
}