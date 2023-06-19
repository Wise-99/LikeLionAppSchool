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

            textViewBeforeTitle.text = beforeTitle
            textViewBeforeContent.text = beforeContent

            buttonMemoUpdate.setOnClickListener {
                val updateTitle = editTextUpdateMemoTitle.text.toString()
                val updateContent = editTextUpdateMemoContent.text.toString()

                val updateIntent = Intent()
                updateIntent.putExtra("newTitle", updateTitle)
                updateIntent.putExtra("newContent", updateContent)

                updateIntent.putExtra("beforeTitle", beforeTitle)
                updateIntent.putExtra("beforeContent", beforeContent)

                updateIntent.putExtra("memoPosition", memoPosition)
                setResult(RESULT_FIRST_USER+1, updateIntent)

                finish()
            }

            buttonMemoUpdateCancel.setOnClickListener {
                finish()
            }
        }
    }
}