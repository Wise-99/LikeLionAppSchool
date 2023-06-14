package com.test.android32_listviewex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import com.test.android32_listviewex01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // ListView를 구성하기 위해 필요한 데이터가 담긴 Map을 담을 List
    val listData = mutableListOf<MutableMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            listViewResult.run {

                val keys = arrayOf(
                    "name", "age", "korean"
                )

                val ids = intArrayOf(
                    R.id.textViewRowName, R.id.textViewRowAge, R.id.textViewRowKorean
                )

                adapter = SimpleAdapter(
                    this@MainActivity, listData, R.layout.row, keys, ids
                )
            }

            editTextKorean.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    val map = mutableMapOf<String, String>()
                    map["name"] = editTextName.text.toString()
                    map["age"] = editTextAge.text.toString()
                    map["korean"] = editTextKorean.text.toString()

                    listData.add(map)

                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    val adapter = listViewResult.adapter as SimpleAdapter
                    adapter.notifyDataSetChanged()

                    false


                }
            }
        }
    }
}