package com.sitamrock11.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sitamrock11.todo.Room.Todo
import kotlinx.android.synthetic.main.add_task.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt

class RVAdapter(private val context: Context, val listener: IRVAdapter) :
    RecyclerView.Adapter<RVAdapter.TodoViewHolder>() {
    val allNotes = ArrayList<Todo>()

   inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDueDate)
        val tvDueTime: TextView = itemView.findViewById(R.id.tvDueTime)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)
       fun bind() {
           with(itemView) {
               val colors = resources.getIntArray(R.array.random_color)
               val randomColor = colors[Random().nextInt(colors.size)]
               viewColorTag.setBackgroundColor(randomColor)
           }
       }

    }
//Here overriding the function getItemId
    override fun getItemId(position: Int): Long {
        return allNotes[position].id
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val viewHolder =
            TodoViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))

        viewHolder.btnDelete.setOnClickListener {
            listener.onDeleteBtnClicked(allNotes[viewHolder.adapterPosition])
        }
      //   id=allNotes[viewHolder.adapterPosition].id
        return viewHolder
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = allNotes[position]
        holder.tvTitle.text = currentTodo.title
        holder.tvCategory.text = currentTodo.category
        holder.tvDescription.text = currentTodo.description
        updateTime(holder, currentTodo)
        updateDate(holder, currentTodo)
        holder.bind()
    }

    private fun updateDate(holder: TodoViewHolder, currentTodo: Todo) {
        //Sun, 14-Mar,2021
        val format = "EEE, d-MMM,yyyy"
        val date = SimpleDateFormat(format).format(Date(currentTodo.time))
        holder.tvDate.text = date
    }

    private fun updateTime(holder: TodoViewHolder, currentTodo: Todo) {
        //02:49 pm
        val myTimeFormat = "hh:mm a"
        val time = SimpleDateFormat(myTimeFormat).format(Date(currentTodo.time))
        holder.tvDueTime.text = time
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Todo>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface IRVAdapter {
    fun onDeleteBtnClicked(todo: Todo)
}