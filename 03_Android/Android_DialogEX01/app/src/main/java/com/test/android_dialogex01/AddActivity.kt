package com.test.android_dialogex01

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import com.test.android_dialogex01.DataClass.Companion.personList
import com.test.android_dialogex01.databinding.ActivityAddBinding
import java.util.Calendar

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    val hobbyList = arrayOf(
        "영화 감상", "독서", "헬스", "골프", "낚시", "악기 연주", "게임"
    )

    val mutilChoiceList = BooleanArray(hobbyList.size){i -> false}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        var date = ""
        var gender = ""
        var hobby = ""

        activityAddBinding.run {
            buttonDate.setOnClickListener {

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                var month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                // 날짜를 선택하면 동작할 리스너
                val datePickerListener = object : DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        date = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    }
                }

                val pickerDialog = DatePickerDialog(this@AddActivity, datePickerListener, year, month, day)

                pickerDialog.show()
            }

            buttonGender.setOnClickListener {
                val builder = AlertDialog.Builder(this@AddActivity)

                builder.setTitle("성별 선택")
                builder.setPositiveButton("남자"){ dialogInterface: DialogInterface, i: Int ->
                    gender = "남자"
                }
                builder.setNegativeButton("여자"){ dialogInterface: DialogInterface, i: Int ->
                    gender = "여자"
                }

                builder.show()
            }

            buttonHobby.setOnClickListener {
                val builder = AlertDialog.Builder(this@AddActivity)
                builder.setTitle("취미 선택 - 다중 선택 가능")

                builder.setMultiChoiceItems(hobbyList, mutilChoiceList){ dialogInterface: DialogInterface, i: Int, b: Boolean ->
                    mutilChoiceList[i] = b
                }

                builder.setNegativeButton("취소", null)
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

                    for (idx in 0 until mutilChoiceList.size){
                        if(mutilChoiceList[idx]) {
                            hobby += "${hobbyList[idx]}"
                        }
                    }
                }

                builder.show()
            }

            buttonSave.setOnClickListener {
                val name = editTextName.text.toString()

                val person = PersonClass(name, date, gender, hobby)
                personList.add(person)

                finish()
            }

            buttonCancel.setOnClickListener {
                finish()
            }
        }
    }
}