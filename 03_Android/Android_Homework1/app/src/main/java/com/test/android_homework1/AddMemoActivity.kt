package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_homework1.databinding.ActivityAddMemoBinding

class AddMemoActivity : AppCompatActivity() {

    lateinit var activityAddMemoBinding: ActivityAddMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddMemoBinding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(activityAddMemoBinding.root)

        activityAddMemoBinding.run {
            // 메모 추가
            buttonAddMemo.setOnClickListener {
                val addIntent = Intent()
                val title = editTextAddMemoTitle.text.toString()
                val content = editTextAddMemoContent.text.toString()

                // 제목과 내용 전달
                addIntent.putExtra("newTitle", title)
                addIntent.putExtra("newContent", content)
                setResult(RESULT_OK, addIntent)

                finish()
            }

            // 메모 추가 취소
            buttonAddMemoCancel.setOnClickListener {
                finish()
            }
        }
    }
}