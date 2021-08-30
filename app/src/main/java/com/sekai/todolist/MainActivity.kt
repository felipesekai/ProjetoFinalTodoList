package com.sekai.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sekai.todolist.databinding.ActivityMainBinding
import com.sekai.todolist.datasource.TaskDataSource
import com.sekai.todolist.model.Task
import com.sekai.todolist.ui.ActivityAddTarefas
import com.sekai.todolist.ui.ListAdapter

class MainActivity : AppCompatActivity() {
    private val adapter by lazy { ListAdapter() }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickListener()
        startList()

    }

    override fun onResume() {
        super.onResume()
        startList()
    }

    private fun onClickListener() {
        binding.addTarefa.setOnClickListener {
            val intent = Intent(this, ActivityAddTarefas::class.java)
//           startActivityForResult(intent , RESQUEST_CODE)
            startActivity(intent)

        }

        adapter.listenerEdit = {
            val intent = Intent(this, ActivityAddTarefas::class.java)
            intent.putExtra(ITEM_EDIT, it)
            startActivity(intent)
        }
        adapter.listenerDelete = {
            try {
                TaskDataSource.setHelper(this).deleteItem(it)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            startList()
        }

    }


    private fun startList() {
        var list: ArrayList<Task>? = null

        try {
            list = TaskDataSource.setHelper(this).getAllList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (list?.isEmpty() == true) {
            binding.rvListView.visibility = View.GONE
            binding.includeEmppyTasks.root.visibility = View.VISIBLE
        } else {
            binding.includeEmppyTasks.root.visibility = View.GONE
            binding.rvListView.visibility = View.VISIBLE
            adapter.updateList(list!!)
            binding.rvListView.adapter = adapter
        }

    }

    companion object {
        const val ITEM_EDIT = "ITEM EDIT"
    }


}