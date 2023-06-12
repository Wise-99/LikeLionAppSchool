package com.test.android_zoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.test.android_zoo.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var imm : InputMethodManager

    val animalList = mutableListOf<Animal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        activityMainBinding.run {

            editTextName.requestFocus()
            thread {
                SystemClock.sleep(500)
                imm.showSoftInput(currentFocus, 0)
            }

            radioGroup.run {
                setOnCheckedChangeListener { radioGroup, i ->
                    when(checkedRadioButtonId){
                        R.id.radioButtonElephant -> {
                            editTextLength.visibility = View.VISIBLE
                            editTextTalent.visibility = View.GONE
                            editTextSpeed.visibility = View.GONE
                        }

                        R.id.radioButtonCat -> {
                            editTextLength.visibility = View.GONE
                            editTextTalent.visibility = View.GONE
                            editTextSpeed.visibility = View.VISIBLE
                        }

                        R.id.radioButtonDog -> {
                            editTextLength.visibility = View.GONE
                            editTextTalent.visibility = View.VISIBLE
                            editTextSpeed.visibility = View.GONE
                        }
                    }
                }

            }

            editTextLength.run {
                setOnEditorActionListener { textView, i, keyEvent ->

                    editTextEnter()
                    false
                }
            }

            editTextSpeed.run{
                setOnEditorActionListener { textView, i, keyEvent ->

                    editTextEnter()
                    false
                }
            }

            editTextTalent.run {
                setOnEditorActionListener { textView, i, keyEvent ->

                    editTextEnter()
                    false
                }
            }

            buttonOutput.run {
                setOnClickListener {
                    if(currentFocus != null){
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        currentFocus!!.clearFocus()
                    }

                    textView.setText("")

                    var elephantTotal = 0
                    var catTotal = 0
                    var dogTotal = 0

                    for (a1 in animalList){
                        textView.append("타입 : ${a1.type}\n")
                        textView.append("먹이 : ${a1.peed}\n")
                        textView.append("이름 : ${a1.name}\n")

                        when(a1.type){
                            "코끼리" -> {
                                textView.append("코의 길이 : ${a1.etc}\n\n")
                                elephantTotal++
                            }
                            "고양이" -> {
                                textView.append("냥펀치 속도 : ${a1.etc}\n\n")
                                catTotal++
                            }
                            "강아지" -> {
                                textView.append("개인기 갯수 : ${a1.etc}\n\n")
                                dogTotal++
                            }
                        }
                    }
                    textView.append("코끼리 : ${elephantTotal}마리\n")
                    textView.append("고양이 : ${catTotal}마리\n")
                    textView.append("강아지 : ${dogTotal}마리\n")

                }
            }
        }
    }

    fun editTextEnter(){
        val name = activityMainBinding.editTextName.text.toString()
        var etc = 0
        var type = ""
        var peed = ""

        when(activityMainBinding.radioGroup.checkedRadioButtonId){
            R.id.radioButtonElephant -> {
                type = "코끼리"
                peed = "나뭇잎"
                etc = activityMainBinding.editTextLength.text.toString().toInt()
            }
            R.id.radioButtonCat -> {
                type = "고양이"
                peed = "고양이 사료"
                etc = activityMainBinding.editTextSpeed.text.toString().toInt()
            }
            R.id.radioButtonDog -> {
                type = "강아지"
                peed = "강아지 사료"
                etc = activityMainBinding.editTextTalent.text.toString().toInt()
            }
        }

        val animal = Animal(type, peed, name, etc)
        animalList.add(animal)

        activityMainBinding.editTextName.setText("")
        activityMainBinding.editTextLength.setText("")
        activityMainBinding.editTextSpeed.setText("")
        activityMainBinding.editTextTalent.setText("")
        activityMainBinding.radioGroup.check(R.id.radioButtonElephant)

        activityMainBinding.editTextName.requestFocus()
    }
}

data class Animal(var type:String, var peed:String, var name:String, var etc:Int)