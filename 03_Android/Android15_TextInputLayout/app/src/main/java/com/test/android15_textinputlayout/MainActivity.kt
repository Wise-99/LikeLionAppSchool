package com.test.android15_textinputlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.test.android15_textinputlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run{

            textInputLayout.run{
                // 오류 문구 설정
                // 입력 하기도 전에 오류가 발생했다고 표시됨
                // error = "입력 오류가 발생하였습니다"

                editText?.run {
                    addTextChangedListener {
                        if (it!!.length > 10){
                            // error = "10글자 이하로 입력해주세요"
                            textInputLayout.error = "10글자 이하로 입력해주세요"
                        } else {
                            // error = null
                            textInputLayout.error = null
                        }
                    }
                }
            }

            button.run{
                setOnClickListener {
                    // 입력한 내용을 가져온다.
                    // val str1 = textInputEditText.text.toString()
                    // textView.text = str1

                    // TextInputLayout으로 부터 EditText를 추출한다.
                    if(textInputLayout.editText != null) {
                        val str1 = textInputLayout.editText!!.text.toString()
                        textView.text = str1
                    }
                }
            }
        }
    }
}