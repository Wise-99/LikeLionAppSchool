package com.test.mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.mvvm.databinding.ActivityMainBinding
import com.test.mvvm.databinding.RowBinding
import com.test.mvvm.vm.ViewModelTest1
import com.test.mvvm.vm.ViewModelTest2

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // viewModel
    lateinit var viewModelTest1: ViewModelTest1
    lateinit var viewModelTest2: ViewModelTest2

    companion object{
        lateinit var mainActivity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainActivity = this

        // ViewModel 객체를 받아온다.
        viewModelTest1 = ViewModelProvider(this)[ViewModelTest1::class.java]
        viewModelTest2 = ViewModelProvider(this)[ViewModelTest2::class.java]

        val testViewModel = ViewModelProvider(this).get<TestViewModel>()
        testViewModel.number1.observe(this){
            activityMainBinding.textView3.text = "number : $it"
        }

        testViewModel.number1.value = 0

        activityMainBinding.run{

            buttonMain.setOnClickListener {
                val newIntent = Intent(this@MainActivity, AddActivity::class.java)

                startActivity(newIntent)
            }

            recyclerViewMain.run{
                adapter = MainRecyclerAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL))
            }

            viewModelTest2.run{
                dataList.observe(this@MainActivity){
                    recyclerViewMain.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    inner class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>(){
        inner class MainViewHolder(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRow:TextView

            init{
                textViewRow = rowBinding.textViewRow

                rowBinding.root.setOnClickListener {
                    val newIntent = Intent(this@MainActivity, ResultActivity::class.java)

                    // 값을 가지고 있는 객체를 추출한다.
                    val t1 = viewModelTest2.dataList.value?.get(adapterPosition)

                    // 항목 번째의 데이터를 가져온다.

                    // ViewModel 객체에 새로운 값을 설정한다.
                    // viewModelTest1.data1.value = t1?.data1!!
                    // viewModelTest1.data2.value = t1?.data2!!

                    startActivity(newIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val mainViewHolder = MainViewHolder(rowBinding)

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return viewModelTest2.dataList.value?.size!!
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.textViewRow.text = viewModelTest2.dataList.value?.get(position)?.data1
        }
    }

    override fun onResume() {
        super.onResume()
        // ViewModel 에 있는 모든 데이터를 가져오는 메서드를 호출한다.
        viewModelTest2.getAll()
    }
}

class TestViewModel : ViewModel(){
    val number1 = MutableLiveData<Int>()
}