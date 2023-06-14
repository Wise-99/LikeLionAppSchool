package com.test.android_customadapterex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.android_customadapterex01.databinding.ActivityMainBinding
import com.test.android_customadapterex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    val dataList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            editTextText.run {
                setOnEditorActionListener { v, actionId, event ->
                    val data = editTextText.text.toString()

                    dataList.add(data)

                    editTextText.setText("")

                    false
                }
            }

            listView.run {
                adapter = CustomAdapter()
            }
        }

    }

    inner class CustomAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return dataList.size
        }

        override fun getItem(position: Int): Any? {
            // TODO("Not yet implemented")
            return null
        }

        override fun getItemId(position: Int): Long {
            // TODO("Not yet implemented")
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowBinding : RowBinding? = null
            var mainView = convertView

            if (mainView == null){
                rowBinding = RowBinding.inflate(layoutInflater)
                mainView = rowBinding.root
                mainView!!.tag = rowBinding
            } else {
                rowBinding = mainView!!.tag as RowBinding
            }

            rowBinding.run {
                textView.run {
                    text = dataList[position]
                }

                button.run {
                    setOnClickListener {
                        dataList.removeAt(position)

                        val adapter = activityMainBinding.listView.adapter as CustomAdapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            return mainView


        }
    }
}