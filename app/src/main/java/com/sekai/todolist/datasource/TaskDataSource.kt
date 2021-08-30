package com.sekai.todolist.datasource

import android.app.Application
import android.content.Context
import com.sekai.todolist.db.HelperDb
import com.sekai.todolist.model.Task

object TaskDataSource: Application() {
    private lateinit var helperDb: HelperDb

    private var list = arrayListOf<Task>()

    fun get() = list
    fun setList(itemTask: Task){
        list.add(itemTask.copy(id = list.size + 1))
    }
    fun setHelper(context: Context): HelperDb { helperDb = HelperDb(context)
        return helperDb}


}