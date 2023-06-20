package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_homework1.databinding.ActivityUpdateMemoBinding

class UpdateMemoActivity : AppCompatActivity() {
    lateinit var activityUpdateMemoBinding: ActivityUpdateMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityUpdateMemoBinding = ActivityUpdateMemoBinding.inflate(layoutInflater)
        setContentView(activityUpdateMemoBinding.root)

        activityUpdateMemoBinding.run {
            val beforeTitle = intent.getStringExtra("memoTitle")
            val beforeContent = intent.getStringExtra("memoContent")
            val memoPosition = intent.getIntExtra("memoPosition", 0)

            textViewBeforeTitle.append(beforeTitle)
            textViewBeforeContent.append(beforeContent)

            // 수정 버튼 클릭 시
            buttonMemoUpdate.setOnClickListener {
                val updateTitle = editTextUpdateMemoTitle.text.toString()
                val updateContent = editTextUpdateMemoContent.text.toString()

                val updateIntent = Intent()
                updateIntent.putExtra("newTitle", updateTitle)
                updateIntent.putExtra("newContent", updateContent)

                updateIntent.putExtra("beforeTitle", beforeTitle)
                updateIntent.putExtra("memoPosition", memoPosition)

                setResult(RESULT_FIRST_USER+1, updateIntent)
                finish()
            }
            // 취소 버튼 클릭시 그대로 종료
            buttonMemoUpdateCancel.setOnClickListener {
                finish()
            }
        }
    }
}