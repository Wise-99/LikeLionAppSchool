package com.test.android45_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android45_ex01.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        activityShowBinding.run {
            val position = intent.getIntExtra("position",0)

            textViewType.text = DataClass.fruitList[position].type
            textViewVolume.text = "${DataClass.fruitList[position].volume}개"
            textViewRegion.text = DataClass.fruitList[position].region

            buttonToMain2.run {
                setOnClickListener {
                    finish()
                }
            }
        }
    }
}