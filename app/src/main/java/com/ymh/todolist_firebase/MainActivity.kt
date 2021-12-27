package com.ymh.todolist_firebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ymh.todolist_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val db = Firebase.firestore
    /*private val itemList = arrayListOf<ListLayout>()
    private val adapter: ListAdapter
        get() = ListAdapter(itemList)*/


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = ListAdapter()

        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        binding.refreshBtn.setOnClickListener {
            db.collection("Contacts")
                .get()
                .addOnSuccessListener { result ->
                    //itemList.clear()
                    val items = arrayListOf<ListLayout>()
                    for (document in result) {
                        Log.d("test", "adasdasd")
                        val item = ListLayout(document["name"] as String)
                        items.add(item)
                        //itemList.add(item)
                    }
                    adapter.setItems(items)
                    //adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }


    }
}