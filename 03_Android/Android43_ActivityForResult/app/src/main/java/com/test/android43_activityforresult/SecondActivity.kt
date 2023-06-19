package com.test.android43_activityforresult

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android43_activityforresult.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var secondBinding : ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        secondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)

        secondBinding.run {

            // Activity를 실행하기 위해 사용한 Intent로부터 데이터를 추출한다.
            textViewSecond.text = "SecondActivity\n"

            // 기본 자료형에 관련된 메서드들을 데이터의 이름과 저장된 것이 없을 경우 사용할 기본 값을 설정한다.
            val data1 = intent.getIntExtra("data1", 0)
            val data2 = intent.getDoubleExtra("data2",0.0)
            val data3 = intent.getBooleanExtra("data3", false)
            // 객체는 기본값을 설정하지 않으며 값이 없을 경우에는 null이 반한된다.
            val data4 = intent.getStringExtra("data4")

            textViewSecond.append("data1 : ${data1}\n")
            textViewSecond.append("data1 : ${data2}\n")
            textViewSecond.append("data1 : ${data3}\n")
            textViewSecond.append("data1 : ${data4}\n")

            // 객체를 복원한다.
            // SDK 버전별 처리
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                val data5 = intent.getParcelableExtra("data5", TestClass::class.java)
                textViewSecond.append("data5.name : ${data5?.name}")
                textViewSecond.append("data5.age : ${data5?.age}")
            } else {
                val data5 = intent.getParcelableExtra<TestClass>("data5")
                textViewSecond.append("data5.name : ${data5?.name}")
                textViewSecond.append("data5.age : ${data5?.age}")
            }





            buttonSecond.run {
                setOnClickListener {
                    // 작업의 결과를 설정해준다.
                    // RESULT_OK : 작업이 정상적으로 끝났다는 것을 의미한다.
                    // RESULT_CANCELED : 작업이 취소되었다는 것을 의미한다.
                    // RESULT_FIRST_USER : 작업의 상황을 더 추가적으로 정의하고 싶을 때(+1, +2, +3...)
                    // setResult(RESULT_OK)
                    // setResult(RESULT_CANCELED)

                    // 이전 Activity로 전달할 데이터를 설정할 Intent 객체를 생성한다.
                    val resultIntent = Intent()
                    // 값을 설정한다.
                    resultIntent.putExtra("value1", 200)
                    resultIntent.putExtra("value2", 22.22)
                    resultIntent.putExtra("value3", false)
                    resultIntent.putExtra("value4" , "반갑습니다")

                    // 돌아갈 때 전달할 값이 있다면
                    // setResult 메서드의 두번째 매개 변수에 Intent 객체를 넣어준다.
                    setResult(RESULT_OK, resultIntent)

                    // Activity 종료
                    finish()
                }
            }
        }
    }
}