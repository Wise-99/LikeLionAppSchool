package com.test.android44_ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android44_ex01.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        activityAddBinding.run {
            editTextData2.run {
                setOnEditorActionListener { v, actionId, event ->
                    // 입력한 내용을 가져온다.
                    val data1 = editTextData1.text.toString()
                    val data2 = editTextData2.text.toString()
                    // Intent에 담아준다.
                    val resultIntent = Intent()
                    resultIntent.putExtra("data1", data1)
                    resultIntent.putExtra("data2", data2)

                    setResult(RESULT_OK, resultIntent)

                    finish()

                    false
                }
            }
        }
    }
}