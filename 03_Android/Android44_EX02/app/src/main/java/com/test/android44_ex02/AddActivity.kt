package com.test.android44_ex02

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.test.android44_ex02.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding
    val spinnerList = arrayOf("수박", "사과", "귤") // 스피너 메뉴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        activityAddBinding.run {
            spinner.run {
                // 어뎁터 설정
                val a1 = ArrayAdapter<String>(
                    this@AddActivity,
                    // Sinner가 접혀있을 때의 모양
                    R.layout.simple_spinner_item,
                    spinnerList
                )
                // Sinner가 펼쳐져 있을 때의 모양
                a1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                adapter = a1
            }

            editTextCountry.run {
                buttonAdd.run {
                    setOnClickListener {
                        val name = spinnerList[spinner.selectedItemPosition]
                        val number = editTextNumber.text.toString().toInt()
                        val country = editTextCountry.text.toString()

                        val resultIntent = Intent()
                        resultIntent.putExtra("name", name)
                        resultIntent.putExtra("number", number)
                        resultIntent.putExtra("country", country)

                        setResult(RESULT_OK,resultIntent)

                        finish()
                    }
                }
            }
        }
    }
}