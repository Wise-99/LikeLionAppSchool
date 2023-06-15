package com.test.android_recyclerviewex02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.test.android_recyclerviewex02.databinding.ActivityMainBinding
import com.test.android_recyclerviewex02.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            buttonAdd.run {
                setOnClickListener {
                    editTextName.visibility = View.VISIBLE
                    editTextAge.visibility = View.VISIBLE
                    editTextKorean.visibility = View.VISIBLE
                }
            }

            buttonResult.run {
                setOnClickListener {
                    recyclerView.adapter = RecyclerViewAdapter()
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                    if(currentFocus != null){
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        currentFocus!!.clearFocus()
                    }
                }
            }

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString().toInt()
                    val korean = editTextKorean.text.toString().toInt()

                    val student = Student(name, age, korean)
                    studentList.add(student)

                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    editTextName.requestFocus()

                    false
                }
            }
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewName : TextView
            var textViewAge : TextView
            var textViewKorean : TextView
            var buttonDelete : Button

            init {
                textViewName = rowBinding.textViewName
                textViewAge = rowBinding.textViewAge
                textViewKorean = rowBinding.textViewKorean
                buttonDelete = rowBinding.buttonDelete
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowBinding.inflate(layoutInflater)
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
            val (name, age, korean) = studentList[position]

            holder.textViewName.text = name
            holder.textViewAge.text = "${age}세"
            holder.textViewKorean.text = "${korean}점"

            holder.buttonDelete.setOnClickListener {
                studentList.removeAt(position)

                val adapter = binding.recyclerView.adapter as RecyclerViewAdapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    data class Student(var name:String, var age:Int, var korean:Int)
}