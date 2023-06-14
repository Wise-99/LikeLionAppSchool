package com.test.android_recyclerviewex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android_recyclerviewex01.databinding.ActivityMainBinding
import com.test.android_recyclerviewex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            recyclerView.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString()
                    val korean = editTextKorean.text.toString()

                    val student = Student(name, age, korean)
                    studentList.add(student)

                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    false
                }
            }
        }
    }

    // 리사이클러뷰 어뎁터 클래스
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : ViewHolder(rowBinding.root) {
            val textViewRowName : TextView
            val textViewRowAge : TextView
            val textViewRowKorean : TextView

            init {
                textViewRowName = rowBinding.textViewRowName
                textViewRowAge = rowBinding.textViewRowAge
                textViewRowKorean = rowBinding.textViewRowKorean
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowName.text = studentList[position].name
            holder.textViewRowAge.text = studentList[position].age
            holder.textViewRowKorean.text = studentList[position].korean
        }
    }

    data class Student(var name:String, var age:String, var korean:String)
}