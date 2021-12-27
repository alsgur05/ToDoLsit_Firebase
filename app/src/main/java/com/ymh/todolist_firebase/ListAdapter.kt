package com.ymh.todolist_firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(): RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    //private val itemList: ArrayList<ListLayout>

    val itemList = arrayListOf<ListLayout>()

    fun setItems(items: ArrayList<ListLayout>){
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
            return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
            holder.name.text = itemList[position].name
        }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val name: CheckBox = itemView.findViewById(R.id.checkbox1)
        }

}