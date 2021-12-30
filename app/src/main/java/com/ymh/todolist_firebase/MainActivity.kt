package com.ymh.todolist_firebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ymh.todolist_firebase.databinding.ActivityMainBinding
import org.w3c.dom.Text

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
                        val item = ListLayout(document["name"] as String)
                        items.add(item)
                        //itemList.add(item)
                    }
                    adapter.setItems(items)
                    //adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity ", "Error getting documents: $exception")
                }
        }

        binding.plusBtn.setOnClickListener {
            //대화상자 생성
            val builder = AlertDialog.Builder(this)

            val tvName = TextView(this)
            tvName.text = "Name"
            val etName = EditText(this)
            etName.isSingleLine = true
            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)
            mLayout.addView(etName)
            builder.setView(mLayout)

            builder.setTitle("데이터 추가")
            builder.setPositiveButton("추가") { dialog, which ->

                val data = hashSetOf(
                    "name" to etName.text.toString()
                )

                db.collection("Contacts").add(data).addOnSuccessListener {
                    Toast.makeText(this, "데이터가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}