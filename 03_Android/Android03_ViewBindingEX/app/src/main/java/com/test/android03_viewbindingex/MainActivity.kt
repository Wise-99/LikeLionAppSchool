package com.test.android03_viewbindingex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android03_viewbindingex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ViewBinding 객체를 담을 변수
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.button.setOnClickListener {
            var sum = 0
            for (i in 1..100){
                sum += i
            }

            binding.textView.text = "1 ~ 100까지의 합 : $sum"
        }

        binding.button2.setOnClickListener {
            var sum = 0
            for (i in 101 .. 200){
                sum += i
            }

            binding.textView.text = "101 ~ 200까지의 합 : $sum"
        }*/

        binding.run{
            // 첫번째 버튼
            button.run{
                setOnClickListener {
                    var total = 0
                    for (a1 in 1 .. 100){
                        total += a1
                    }
                    binding.textView.text = "1 ~ 100까지의 총합 : $total"
                }
            }

            // 두번째 버튼
            button2.run{
                setOnClickListener {
                    var total = 0
                    for (a1 in 101 .. 200){
                        total += a1
                    }
                    binding.textView.text = "101 ~ 200까지의 총합 : $total"
                }
            }
        }
    }
}