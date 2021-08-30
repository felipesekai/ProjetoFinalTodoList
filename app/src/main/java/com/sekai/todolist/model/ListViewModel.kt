package com.sekai.todolist.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class ListViewModel : ViewModel() {
    private val listaSave = arrayListOf<Task>()
    private var value = MutableLiveData<ArrayList<Task>>()

    fun setList(lista: ArrayList<Task>)  {
        listaSave.addAll(lista)
        setValue()
    }

    private fun setValue(){
        value.apply{
            value?.addAll(listaSave)
        }
    }

    fun getList() = value


}