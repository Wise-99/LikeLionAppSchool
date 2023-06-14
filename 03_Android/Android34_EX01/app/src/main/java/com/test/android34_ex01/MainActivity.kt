package com.test.android34_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.android34_ex01.databinding.ActivityMainBinding
import com.test.android34_ex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            listView.run {
                adapter = CustomAdapter()
            }

            editTextInput.run{
                setOnEditorActionListener { v, actionId, event ->
                    // 사용자가 입력한 문자열을 리스트에 담는다.
                    dataList.add(text.toString())

                    // EditText를 비운다
                    setText("")

                    // 리스트뷰 갱신
                    val adapter = listView.adapter as CustomAdapter
                    adapter.notifyDataSetChanged()

                    false
                }
            }
        }
    }

    // 어뎁터 클래스
    inner class CustomAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return dataList.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowBinding : RowBinding
            var mainView = convertView

            if(convertView == null){
                rowBinding = RowBinding.inflate(layoutInflater)
                mainView = rowBinding.root
                mainView.tag = rowBinding
            } else {
                rowBinding = mainView!!.tag as RowBinding
            }

            rowBinding.run {
                textViewRow.run {
                    text = dataList[position]
                }

                buttonRow.run {
                    setOnClickListener {
                        // position 번째 문자열을 삭제한다.
                        dataList.removeAt(position)
                        // 리스트뷰 갱신
                        val adapter = activityMainBinding.listView.adapter as CustomAdapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            return mainView
        }

    }
}