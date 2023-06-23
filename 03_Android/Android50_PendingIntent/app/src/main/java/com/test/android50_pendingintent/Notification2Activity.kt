package com.test.android50_pendingintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android50_pendingintent.databinding.ActivityNotification2Binding

class Notification2Activity : AppCompatActivity() {

    lateinit var activityNotification2Binding: ActivityNotification2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNotification2Binding = ActivityNotification2Binding.inflate(layoutInflater)
        setContentView(activityNotification2Binding.root)

        activityNotification2Binding.run {
            textViewNo2.run {
                val data3 = intent.getIntExtra("data3", 0)
                val data4 = intent.getStringExtra("data4")

                text = "data3 : ${data3}\n"
                append("data4 : ${data4}")
            }
        }
    }
}