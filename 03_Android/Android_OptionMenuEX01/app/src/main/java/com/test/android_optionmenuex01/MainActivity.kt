package com.test.android_optionmenuex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android_optionmenuex01.databinding.ActivityMainBinding
import com.test.android_optionmenuex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {

            layoutAdd.visibility = View.GONE
            layoutShow.visibility = View.GONE

            registerForContextMenu(recyclerView)

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString().toInt()
                    val korean = editTextKorean.text.toString().toInt()

                    val student = Student(name, age, korean)
                    studentList.add(student)

                    // 초기화
                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    // 포커스
                    editTextName.requestFocus()

                    false
                }
            }

            recyclerView.run {
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    // 옵션 메뉴 구성 메서드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opion_menu, menu)
        return true
    }

    // 옵션 메뉴에서 메뉴 항목을 선택하면 호출되는 메서드
    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        activityMainBinding.run {
            when(item.itemId){
                // 화면 전환
                R.id.option_add -> {
                    layoutAdd.visibility = View.VISIBLE
                    layoutShow.visibility = View.GONE
                }
                R.id.option_show -> {
                    layoutAdd.visibility = View.GONE
                    layoutShow.visibility = View.VISIBLE

                    // 리사이클러뷰 데이터 변경
                    val adapter = recyclerView.adapter as RecyclerViewAdapter
                    adapter.notifyDataSetChanged()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    data class Student(var name:String, var age:Int, var korean:Int)

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){
        inner class ViewHolderClass(rowBinding: RowBinding) : ViewHolder(rowBinding.root) {
            var textViewName : TextView
            var textViewAge : TextView
            var textViewKorean : TextView

            init {
                textViewName = rowBinding.textViewRowName
                textViewAge = rowBinding.textViewRowAge
                textViewKorean = rowBinding.textViewRowKorean

                rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    menu.setHeaderTitle("${studentList[adapterPosition].name}")
                    menuInflater.inflate(R.menu.context_menu, menu)

                    menu[0].setOnMenuItemClickListener {
                        studentList.removeAt(adapterPosition)
                        this@RecyclerViewAdapter.notifyDataSetChanged()
                        false
                    }
                }
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
            val (name, age, korean) = studentList[position]

            holder.textViewName.text = name
            holder.textViewAge.text = age.toString()
            holder.textViewKorean.text = korean.toString()
        }
    }
}