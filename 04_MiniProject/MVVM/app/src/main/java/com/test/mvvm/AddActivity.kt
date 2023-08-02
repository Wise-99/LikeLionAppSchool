package com.test.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.mvvm.databinding.ActivityAddBinding
import com.test.mvvm.vm.TestData
import com.test.mvvm.vm.ViewModelTest2

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    lateinit var viewModelTest2: ViewModelTest2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        // ViewModel을 받아온다.
        viewModelTest2 = ViewModelProvider(MainActivity.mainActivity)[ViewModelTest2::class.java]

        activityAddBinding.run{
            buttonAdd.run{
                setOnClickListener {

                    val data1 = editTextAddData1.text.toString()
                    val data2 = editTextAddData2.text.toString()

                    val t1 = TestData(data1, data2)

                    // ViewModel 객체의 리스트에 담아준다.
                    // 리사이클러뷰 갱신 안됨
                    // value에 값이 추가되는 게 아닌 value(MutableLiveData)에 담긴 리스트에 값을 추가하는 것이라서
                    // viewModelTest2.dataList.value?.add(t1)

                    viewModelTest2.addItem(t1)

                    finish()
                }
            }
        }
    }
}