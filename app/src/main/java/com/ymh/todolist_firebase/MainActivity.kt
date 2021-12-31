package com.ymh.todolist_firebase

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ymh.todolist_firebase.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fun deleteDocument(item: ListLayout) {
            if(item.time==null) return
            db.collection("Contacts")
                .document("test "+item.time!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "삭제했습니다", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "삭제했습니다")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "오류@@@@@@@@@@@", e)
                }
        }

        val adapter = ListAdapter { item -> deleteDocument(item) }

        fun day(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")
            val formatted = current.format(formatter)

            return formatted
        }

        binding.rvList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        binding.refreshBtn.setOnClickListener {
            db.collection("Contacts")
                .get()
                .addOnSuccessListener { result ->
                    //itemList.clear()
                    val items = arrayListOf<ListLayout>()
                    for (document in result) {
                        //val item = document.toObject(ListLayout::class.java)
                        val item = ListLayout(document["name"] as String, document["time"] as String?)
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
            builder.setPositiveButton("저장") { dialog, which ->
                val data = hashSetOf(
                    "name" to etName.text.toString()
                )

                val time = day()

                db.collection("Contacts")
                    .document("test $time")
                    .set(ListLayout(etName.text.toString(), time))
                    .addOnSuccessListener {
                        Toast.makeText(this, "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    }

                    .addOnFailureListener { exception ->
                        Log.w("MainActivity ", "Error getting documents: $exception")
                    }


            }.show()
        }


    }


}