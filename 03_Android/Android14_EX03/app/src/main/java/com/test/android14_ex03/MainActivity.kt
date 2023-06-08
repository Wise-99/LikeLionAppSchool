package com.test.android14_ex03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.test.android14_ex03.databinding.ActivityMainBinding
import kotlin.concurrent.thread
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val studentList = ArrayList<Student>()

        activityMainBinding.run {

            // 키보드 올라오게 설정
            thread {
                SystemClock.sleep(500);
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(currentFocus, 0)
            }

            editTextName.run {
                requestFocus()

                setOnEditorActionListener { textView, i, keyEvent ->
                    false
                }
            }

            editTextAge.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    false
                }
            }

            editTextKorean.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    false
                }
            }

            editTextEnglish.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    false
                }
            }

            editTextMath.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    val stu = Student(
                        editTextName.text.toString(),
                        editTextAge.text.toString().toInt(),
                        editTextKorean.text.toString().toInt(),
                        editTextEnglish.text.toString().toInt(),
                        editTextMath.text.toString().toInt()
                    )

                    studentList.add(stu)

                    editTextName.text.clear()
                    editTextAge.text.clear()
                    editTextKorean.text.clear()
                    editTextKorean.text.clear()
                    editTextEnglish.text.clear()
                    editTextMath.text.clear()

                    var num = textStudentNumber.text.toString().toInt()
                    num++
                    textStudentNumber.text = num.toString()

                    false
                }
            }

            buttonOutput.run {
                setOnClickListener {
                    var koreanTotal = 0
                    var englishTotal = 0
                    var mathTotal = 0

                    for (s in studentList){
                        s.logCatInfo()

                        koreanTotal += s.korean
                        englishTotal += s.english
                        mathTotal += s.math
                    }

                    Log.d("studentInfo","국어 총점 : ${koreanTotal}, 영어 총점 : ${englishTotal}, 수학 총점 : ${mathTotal}")
                    Log.d("studentInfo", "국어 평균 : ${koreanTotal / studentList.size}, 영어 평균 : ${englishTotal / studentList.size}, 수학 평균 : ${mathTotal / studentList.size}")
                }
            }
        }
    }
}

class Student(val name:String, val age:Int, val korean:Int, val english:Int, val math:Int){
    fun logCatInfo(){
        Log.d("studentInfo", "이름 : ${name}, 나이 : ${age}, 국어 : ${korean}, 영어 : ${english}, 수학 : ${math}")
    }
}