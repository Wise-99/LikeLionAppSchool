package com.test.android_listviewex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import com.test.android_listviewex01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val layoutData = intArrayOf(R.id.textViewName, R.id.textViewAge, R.id.textViewKorean)
    val keyData = arrayOf("name", "age", "korean")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {

            val stuList = ArrayList<HashMap<String, Any>>()

            editTextKorean.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    val map = HashMap<String, Any>()
                    map["name"] = editTextName.text.toString()
                    map["age"] = editTextAge.text.toString().toInt()
                    map["korean"] = editTextKorean.text.toString().toInt()

                    stuList.add(map)

                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
                    currentFocus!!.clearFocus()

                    (listView.adapter as SimpleAdapter).notifyDataSetChanged()

                    false
                }
            }

            listView.run {
                adapter = SimpleAdapter(
                    this@MainActivity, stuList, R.layout.row, keyData, layoutData
                )
            }
        }
    }
}