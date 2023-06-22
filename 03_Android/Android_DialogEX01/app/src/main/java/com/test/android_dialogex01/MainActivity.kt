package com.test.android_dialogex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_dialogex01.DataClass.Companion.personList
import com.test.android_dialogex01.databinding.ActivityMainBinding
import com.test.android_dialogex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            buttonAdd.setOnClickListener {
                val addIntent = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(addIntent)
            }

            recyclerViewResult.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            val textViewRowName : TextView

            init {
                textViewRowName = rowBinding.textViewRowName
                rowBinding.root.setOnClickListener {
                    val showIntent = Intent(this@MainActivity, ShowActivity::class.java)
                    showIntent.putExtra("position", adapterPosition)
                    startActivity(showIntent)
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
            return personList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowName.text = personList[position].name
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = activityMainBinding.recyclerViewResult.adapter as RecyclerAdapterClass
        adapter.notifyDataSetChanged()
    }
}