package com.ymh.todolist_firebase

import android.annotation.SuppressLint
import android.content.Context
import android.provider.DocumentsContract.deleteDocument
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class ListAdapter(val onDeleteClick: (ListLayout) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    //private val itemList: ArrayList<ListLayout>

    private val itemList = arrayListOf<ListLayout>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<ListLayout>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_layout, parent, false)

        /*val image = view.findViewById<ImageView>(R.id.ic_delete)
        image.setOnClickListener {  }*/

        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        //holder.name.isChecked = itemList[position].checked ?: false

        holder.image.setOnClickListener {
            Toast.makeText(it.context, "Test", Toast.LENGTH_SHORT).show()
            onDeleteClick(itemList[position])
            //onDeleteClick(position)
        }

        //holder.name.setOnCheckedChangeListener()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.ic_delete)
        val name: CheckBox = itemView.findViewById(R.id.checkbox1)
    }

}

