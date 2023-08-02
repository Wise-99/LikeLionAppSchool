package com.test.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.mvvm.databinding.ActivityResultBinding
import com.test.mvvm.vm.ViewModelTest1

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding

    // ViewModel
    lateinit var viewModelTest2: ViewModelTest1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        // viewModel 객체를 가져온다.
        viewModelTest2 = ViewModelProvider(MainActivity.mainActivity)[ViewModelTest1::class.java]

        activityResultBinding.run {
            // ViewModel 객체가 가지고 있는 프로퍼티에 대한 감시자를 설정한다.
            viewModelTest2.run {
                data1.observe(MainActivity.mainActivity){
                    textView.text = it
                }

                data2.observe(MainActivity.mainActivity){
                    textView2.text = it
                }
            }

            // ViewModel 객체에 새로운 값을 설정한다.
            // viewModelTest2.data1.value = "새로운 문자열1"
            // viewModelTest2.data2.value = "새로운 문자열2"
        }
    }
}