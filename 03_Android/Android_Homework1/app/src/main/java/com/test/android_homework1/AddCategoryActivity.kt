package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_homework1.databinding.ActivityAddCategoryBinding

class AddCategoryActivity : AppCompatActivity() {

    lateinit var activityAddCategoryBinding: ActivityAddCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddCategoryBinding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(activityAddCategoryBinding.root)

        activityAddCategoryBinding.run {
            // 추가 버튼 클릭 시
            buttonCategoryAdd.setOnClickListener {
                val addIntent = Intent()
                addIntent.putExtra("newCategoryName", editTextCategoryName.text.toString())
                setResult(RESULT_OK, addIntent)

                finish()
            }
            // 취소 버튼 클릭 시 그대로 종료
            buttonCategoryAddCancel.setOnClickListener {
                finish()
            }
        }
    }
}