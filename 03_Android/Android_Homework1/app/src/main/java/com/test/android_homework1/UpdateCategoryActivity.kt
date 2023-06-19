package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_homework1.databinding.ActivityUpdateCategoryBinding

class UpdateCategoryActivity : AppCompatActivity() {

    lateinit var activityUpdateCategoryBinding: ActivityUpdateCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityUpdateCategoryBinding = ActivityUpdateCategoryBinding.inflate(layoutInflater)
        setContentView(activityUpdateCategoryBinding.root)

        activityUpdateCategoryBinding.run {
            val beforeCategoryName = intent.getStringExtra("categoryName")
            val categoryPosition = intent.getIntExtra("categoryPosition", 0)

            textViewUpdateCategory.text = beforeCategoryName

            // 카테고리 수정
            buttonCategoryUpdate.setOnClickListener {
                val afterUpdateCategory = editTextUpdateCategory.text.toString()
                val updateIntent = Intent()
                updateIntent.putExtra("beforeCategoryName", beforeCategoryName)
                updateIntent.putExtra("newCategoryName", afterUpdateCategory)
                updateIntent.putExtra("categoryPosition", categoryPosition)
                setResult(RESULT_FIRST_USER, updateIntent)

                finish()
            }

            // 카테고리 수정 취소
            buttonCategoryCancel.setOnClickListener {
                finish()
            }
        }
    }
}