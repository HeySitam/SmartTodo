package com.sitamrock11.todo.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_Table")
data class Todo(
    var title:String,
    var description:String,
    var category:String,
    var date:Long,
    var time:Long,
    var isFinished:Int=-1,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L
)
