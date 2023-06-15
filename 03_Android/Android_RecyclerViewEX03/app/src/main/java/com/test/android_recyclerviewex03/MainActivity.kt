package com.test.android_recyclerviewex03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android_recyclerviewex03.databinding.ActivityMainBinding
import com.test.android_recyclerviewex03.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val soccerList = ArrayList<Soccer>()

    val nameList = arrayOf(
        "토고", "프랑스", "스위스", "스페인", "일본", "독일", "브라질", "대한민국"
    )

    val imgList = arrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {

            layoutInsert.visibility = View.GONE
            layoutShow.visibility = View.GONE

            spinner.run {
                val a1 = ArrayAdapter(
                    this@MainActivity, android.R.layout.simple_spinner_item,
                    nameList
                )
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = a1

                setSelection(0)
            }

            editTextName.run {
                setOnEditorActionListener { v, actionId, event ->
                    val country = spinner.selectedItemPosition

                    var position = ""
                    when(radioGroup.checkedRadioButtonId){
                        R.id.radioFoward -> position = "공격수"
                        R.id.radioMidfielder -> position = "미드필더"
                        R.id.radioDefender -> position = "수비수"
                        R.id.radioGoalkeeper -> position = "골키퍼"
                    }
                    val name = editTextName.text.toString()

                    val soccer = Soccer(name, country, position)
                    soccerList.add(soccer)

                    // 초기화
                    editTextName.setText("")
                    radioGroup.check(R.id.radioFoward)
                    spinner.setSelection(0)

                    false
                }
            }

            buttonInsert.run {
                setOnClickListener {
                    layoutInsert.visibility = View.VISIBLE
                    layoutShow.visibility = View.GONE

                    // 초기화
                    editTextName.setText("")
                    radioGroup.check(R.id.radioFoward)
                    spinner.setSelection(0)
                }
            }

            buttonShow.run {
                setOnClickListener {
                    // 포커스를 제거하고 키보드를 내린다.
                    if(currentFocus != null) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        currentFocus!!.clearFocus()
                    }

                    layoutInsert.visibility = View.GONE
                    layoutShow.visibility = View.VISIBLE

                    val adapter = recyclerViewResult.adapter as RecyclerAdapterClass
                    adapter.notifyDataSetChanged()
                }
            }

            recyclerViewResult.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    data class Soccer(var name:String, var country:Int, var position:String)

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderclass>() {
        inner class ViewHolderclass(rowBinding: RowBinding) : ViewHolder(rowBinding.root){
            var textViewName = rowBinding.textViewName
            var textViewPosition = rowBinding.textViewPosition
            var img = rowBinding.imageView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderclass {
            var rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderclass = ViewHolderclass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowBinding.root.layoutParams = params

            return viewHolderclass
        }

        override fun getItemCount(): Int {
            return soccerList.size
        }

        override fun onBindViewHolder(holder: ViewHolderclass, position: Int) {
            val (name, country, position) = soccerList[position]

            holder.textViewName.text = name
            holder.textViewPosition.text = position
            holder.img.setImageResource(imgList[country])
        }
    }
}