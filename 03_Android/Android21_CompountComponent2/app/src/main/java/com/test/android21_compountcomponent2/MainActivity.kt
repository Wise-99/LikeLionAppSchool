package com.test.android21_compountcomponent2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android21_compountcomponent2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            button.run {
                setOnClickListener {
                    // Toggle Button 의 ON/OFF 상태를 가져온다.
                    if (toggleButton.isChecked) {
                        textView.text = "ON 상태입니다\n"
                    } else {
                        textView.text = "OFF 상태입니다\n"
                    }

                    // Switch의 ON/OFF 상태를 가져온다.
                    if (switch1.isChecked) {
                        textView.append("Switch : ON 상태입니다\n")
                    } else {
                        textView.append("Switch : OFF 상태입니다\n")
                    }
                }

                button2.run {
                    setOnClickListener {
                        // Toggle Button을 ON 상태로 설정한다.
                        toggleButton.isChecked = true
                        // switch를 ON 상태로 설정한다.
                        switch1.isChecked = true
                    }
                }

                button3.run {
                    setOnClickListener {
                        // Toggle Button을 OFF 상태로 설정한다.
                        toggleButton.isChecked = false
                        // switch를 OFF 상태로 설정한다.
                        switch1.isChecked = false
                    }
                }

                button4.run {
                    setOnClickListener {
                        // Toggle Button을 반전시킨다.
                        toggleButton.toggle()
                        // Switch를 반전시킨다.
                        switch1.toggle()
                    }
                }

                // Toggle Button의 ON/OFF 상태가 변결될 때의 리스너
                toggleButton.run {
                    setOnCheckedChangeListener { compoundButton, b ->
                        if (isChecked == true) {
                            textView2.text = "Toggle 버튼이 ON 상태입니다\n"
                        } else {
                            textView2.text = "Toggle 버튼이 OFF 상태입니다\n"
                        }
                    }
                }

                switch1.run{
                    setOnCheckedChangeListener { compoundButton, b ->
                        if (isChecked == true){
                            textView2.append("Switch가 ON 상태 입니다.\n")
                        } else {
                            textView2.append("Switch가 OFF 상태 입니다.\n")
                        }
                    }
                }
            }
        }
    }
}