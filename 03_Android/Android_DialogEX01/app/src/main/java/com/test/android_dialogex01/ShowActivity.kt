package com.test.android_dialogex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_dialogex01.DataClass.Companion.personList
import com.test.android_dialogex01.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        activityShowBinding.run {
            val position = intent.getIntExtra("position", 0)

            textViewName.append(personList[position].name)
            textViewDate.append(personList[position].date)
            textViewGender.append(personList[position].gender)
            textViewHobby.append(personList[position].hobby)
        }
    }
}