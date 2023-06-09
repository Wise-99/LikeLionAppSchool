package com.test.android_compoundcomponentex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.android_compoundcomponentex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val studentList = ArrayList<Student>()

        activityMainBinding.run {

            editTextName.requestFocus()

            editTextKorean.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString().toInt()

                    var gender = ""
                    when(genderGroup.checkedRadioButtonId){
                        R.id.radioBtnMan ->{
                            gender = "남자"
                        }
                        R.id.radioBtnWoman -> {
                            gender = "여자"
                        }
                    }

                    var hobby = ""
                    if(checkBoxGame.isChecked){
                        hobby += "게임, "
                    }
                    if (checkBoxSoccer.isChecked){
                        hobby += "축구, "
                    }
                    if (checkBoxMovie.isChecked){
                        hobby += "영상 시청"
                    }

                    val korean = editTextKorean.text.toString().toInt()

                    val student = Student(name, age, gender, hobby, korean)
                    studentList.add(student)

                    //editText들을 비워준다.
                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    false
                }
            }


            button.run{
                setOnClickListener {

                    // 각 과목별 총점과 평균
                    var koreanTotal = 0

                    // 초기화
                    textView.text = ""

                    // 학생 수만큼 반복한다.
                    for(studentInfo in studentList){
                        // 학생 정보 출력
                        studentInfo.run {
                            textView.append("이름 : ${name}\n")
                            textView.append("나이 : ${age}\n")
                            textView.append("성별 : ${gender}\n")
                            textView.append("취미 : ${hobby}\n")
                            textView.append("국어 점수 : ${korean}\n")
                            textView.append("----------------------------\n")

                            koreanTotal += korean
                        }
                    }

                    // 평균을 구한다.
                    val koreanAvg = koreanTotal / studentList.size

                    textView.append("국어 총점 : ${koreanTotal}\n")
                    textView.append("국어 평균 : ${koreanAvg}\n")
                }
            }
        }
    }
}

data class Student(val name:String, val age:Int, val gender:String, val hobby:String, val korean:Int)