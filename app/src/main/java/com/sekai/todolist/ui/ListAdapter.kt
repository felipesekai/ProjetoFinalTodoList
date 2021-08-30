package com.sekai.todolist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sekai.todolist.R
import com.sekai.todolist.databinding.ItemTaskBinding
import com.sekai.todolist.model.Task

class ListAdapter(
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    val list: ArrayList<Task> = arrayListOf()
    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    fun updateList(lista : ArrayList<Task>){
        list.clear()
        list.addAll(lista)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.txt_card_title)
        val date = itemView.findViewById<TextView>(R.id.txt_card_date)
        val imgView = itemView.findViewById<ImageView>(R.id.iv_more)


        private fun showPopup(item: Task) {
            val popupMenu = PopupMenu(imgView.context, imgView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }

        fun bind(item: Task) {
            title.text = item.title
            date.text = "${item.date} ${item.hour}"
            imgView.setOnClickListener {
                showPopup(item)
            }

        }


    }
}