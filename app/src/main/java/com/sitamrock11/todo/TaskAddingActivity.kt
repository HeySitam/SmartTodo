package com.sitamrock11.todo


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.sitamrock11.todo.Room.Todo
import com.sitamrock11.todo.Room.TodoDatabase
import kotlinx.android.synthetic.main.add_task.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class TaskAddingActivity : AppCompatActivity() {
    lateinit var myCalendar: Calendar

    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    val labels = arrayListOf("Default", "Personal", "Professional", "Shopping", "Wishlist", "Work")

    var finalDate=0L
    var finalTime=0L
    lateinit var title:String
    lateinit var description:String
    lateinit var category:String

    val todoDao by lazy{
        TodoDatabase.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)
        etDate.setOnClickListener {
            setDateListener()
        }
        etTime.setOnClickListener {
            setTimeListener()
        }
        imgAddCategory.setOnClickListener {
            addNewLabel()
        }
        setUpSpinner()
        spinnerItemDeleteDialog()
        btnSaveTask.setOnClickListener {
            saveTodo()
        }
    }

    private fun saveTodo() {
        category = spinnerCategory.selectedItem.toString()
         title = titleInpLay.editText?.text.toString()
         description = taskInpLay.editText?.text.toString()
        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext todoDao.insertTask(
                        Todo(
                                title,
                                description,
                                category,
                                finalDate,
                                finalTime
                        )
                )
            }
            finish()
        }

    }

    private fun spinnerItemDeleteDialog() {
        spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val deleteDialog = AlertDialog.Builder(this@TaskAddingActivity)
                deleteDialog.setTitle("${labels[position]} Category")
                deleteDialog.setMessage("what you want to do?")
                deleteDialog.setPositiveButton("USE") { _, _ ->

                }

                deleteDialog.setNegativeButton("REMOVE") { _, _ ->
                    labels.removeAt(position)
                }
                if (labels[position] != "Default") {
                    deleteDialog.show()
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun addNewLabel() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("LABEL")
        alertDialog.setMessage("Enter New Label")
        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        input.layoutParams = lp
        alertDialog.setView(input)
        alertDialog.setIcon(R.drawable.ic_playlist_add_black_24dp)
        alertDialog.setPositiveButton("ADD"
        ) { _, _ ->
            val newLabel = input.text.toString()
            labels.add(newLabel)
        }
        alertDialog.setNegativeButton("CANCEL") { _, _ ->
            alertDialog.setCancelable(true)
        }
        alertDialog.show()
    }

    private fun setUpSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        spinnerCategory.adapter = adapter

    }

    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()
        timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTime()
        }
        val timePickerDialog = TimePickerDialog(this,
                timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false)
        timePickerDialog.show()

    }

    private fun updateTime() {
        //02:49 pm
        val myTimeFormat = "hh:mm a"
        val stf = SimpleDateFormat(myTimeFormat)
        finalTime=myCalendar.time.time
        etTime.setText(stf.format(myCalendar.time))
    }

    private fun setDateListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }
        val datePickerDialog = DatePickerDialog(
                this, dateSetListener, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        //Sun, 14-Mar,2021
        val format = "EEE, d-MMM,yyyy"
        val sdf = SimpleDateFormat(format)
        finalDate=myCalendar.time.time
        etDate.setText(sdf.format(myCalendar.time))
        tilTime.visibility = View.VISIBLE
    }
}


