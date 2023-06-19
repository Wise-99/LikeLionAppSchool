package com.test.android_homework1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_homework1.databinding.ActivityShowMemoContentBinding

class ShowMemoContentActivity : AppCompatActivity() {

    lateinit var activityShowMemoContentBinding: ActivityShowMemoContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowMemoContentBinding = ActivityShowMemoContentBinding.inflate(layoutInflater)
        setContentView(activityShowMemoContentBinding.root)

        activityShowMemoContentBinding.run {
            val title = intent.getStringExtra("title")
            val content = intent.getStringExtra("content")

            textViewMemoTitle.text = title
            textViewMemoContent.text = content
        }
    }
}