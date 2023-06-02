package com.test.android02_ex01

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView
    lateinit var btn1 : Button
    lateinit var btn2 : Button
    lateinit var btn3 : Button
    lateinit var btn4 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        btn1 = findViewById(R.id.button)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn4 = findViewById(R.id.button4)

        val btn1ClickListener = Btn1ClickListener()
        btn1.setOnClickListener(btn1ClickListener)

        val btn2ClickListener = Btn2ClickListener()
        btn2.setOnClickListener(btn2ClickListener)

        val btn3ClickListener = Btn3ClickListener()
        btn3.setOnClickListener(btn3ClickListener)

        val btn4ClickListener = Btn4ClickListener()
        btn4.setOnClickListener(btn4ClickListener)
    }

    inner class Btn1ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val result = (10 + 10).toString()
            "10 + 10 = $result".also { textView.text = it }
        }
    }

    inner class Btn2ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val result = (10 - 10).toString()
            "10 - 10 = $result".also { textView.text = it }
        }
    }

    inner class Btn3ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val result = (10 * 10).toString()
            "10 * 10 = $result".also { textView.text = it }
        }
    }

    inner class Btn4ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val result = (10 / 10).toString()
            "10 / 10 = $result".also { textView.text = it }
        }
    }
}