package com.sekai.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.sekai.todolist.MainActivity
import com.sekai.todolist.R
import com.sekai.todolist.databinding.ActivityAddTarefasBinding
import com.sekai.todolist.datasource.TaskDataSource
import com.sekai.todolist.extensions.format
import com.sekai.todolist.extensions.text
import com.sekai.todolist.model.Task
import java.util.*

class ActivityAddTarefas : AppCompatActivity() {

    private  lateinit var binding: ActivityAddTarefasBinding
    var resultEdit = false
    var item : Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTarefasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = intent.getParcelableExtra(MainActivity.ITEM_EDIT)

            if(item!=null){
                resultEdit = intent.hasExtra(MainActivity.ITEM_EDIT)
                preenchendoCampos(item!!)
            }


        insertListener()
    }

    private fun preenchendoCampos(item: Task) {

           binding.tilTitle.text = item.title!!
           binding.tilDescription.text = item.description!!
           binding.tilDate.text = item.date!!
           binding.tilTime.text = item.hour!!

            binding.btnSave.text = getString(R.string.save)

    }

    private fun updateItem(item: Task): Task {

            return Task(
                id = item.id,
                title = binding.tilTitle.editText?.text.toString(),
                description = binding.tilDescription.editText?.text.toString(),
                date = binding.tilDate.editText?.text.toString(),
                hour = binding.tilTime.editText?.text.toString(),
            )


    }

    private fun insertListener() {
        binding.tilDate.editText?.setOnClickListener {
            val dataPicker = MaterialDatePicker.Builder.datePicker().build()

            dataPicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time)*-1
                binding.tilDate.text = Date(it + offset).format()
            }
            dataPicker.show(supportFragmentManager, "DATA_PICKER_TAG")
        }

        binding.tilTime.editText?.setOnClickListener{
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener{
               val minute  = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
               val hora = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.tilTime.text = "${hora}:${minute}"
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.btnSave.setOnClickListener{
                val item = Task(
                    id = 0,
                    title = binding.tilTitle.editText?.text.toString(),
                    description = binding.tilDescription.editText?.text.toString(),
                    date = binding.tilDate.editText?.text.toString(),
                    hour = binding.tilTime.editText?.text.toString(),
                )
//            TaskDataSource.setList(item)

            if(resultEdit){
                try{
                    TaskDataSource.setHelper(this).updateItem(updateItem(this.item!!))
                }catch (ex: Exception) {ex.printStackTrace()}
            }else{
                try {
                    TaskDataSource.setHelper(this).insertList(item)
                }catch (e:Exception){ e.printStackTrace() }
            }


            finish()


        }
        binding.btnCancelar.setOnClickListener{
            finish()
        }


    }
}