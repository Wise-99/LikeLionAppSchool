package com.test.android44_ex02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android44_ex02.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        activityShowBinding.run {
            val show = intent.getParcelableExtra<FruitClass>("show")
            textViewName.text = show?.name
            textViewNumber.text = show?.number.toString()
            textViewCountry.text = show?.country.toString()

            buttonGoMain.run {
                setOnClickListener {
                    finish()
                }
            }
        }
    }
}