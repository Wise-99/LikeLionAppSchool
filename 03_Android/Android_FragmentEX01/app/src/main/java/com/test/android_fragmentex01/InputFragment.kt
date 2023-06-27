package com.test.android_fragmentex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.test.android_fragmentex01.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    val typeList = arrayOf("코끼리", "기린", "토끼")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)

        fragmentInputBinding.run {

            spinner.run {
                val a1 = ArrayAdapter<String>(
                    mainActivity,
                    android.R.layout.simple_spinner_item,
                    typeList
                )
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                adapter = a1
                setSelection(0)
            }

            textInputLayoutAge.run {
                editText?.run{
                    addTextChangedListener {
                        if(it!!.toString() == ""){
                            error = "나이는 0 이상의 숫자를 입력해주세요."
                        } else {
                            if(it!!.toString().toInt() <= 0){
                                error = "나이는 0 이상의 숫자를 입력해주세요."
                            } else {
                                error = null
                            }
                        }
                    }
                }
            }

            textInputLayoutKg.run {
                editText?.run {
                    addTextChangedListener {
                        if (it!!.toString() == ""){
                            error = "몸무게는 0 이상의 숫자를 입력해주세요."
                        } else {
                            if (it!!.toString().toInt() <= 0){
                                error = "몸무게는 0 이상의 숫자를 입력해주세요."
                            } else {
                                error = null
                            }
                        }
                    }
                }
            }

            buttonInput.setOnClickListener {
                if (textInputLayoutAge.editText?.error != null){
                    textInputLayoutAge.editText!!.requestFocus()
                }
                else if (textInputLayoutKg.editText?.error != null){
                    textInputLayoutKg.editText!!.requestFocus()
                }

                else {
                    val type = typeList[spinner.selectedItemPosition]
                    val name = editTextInputName.text.toString()
                    val age = textInputLayoutAge.editText!!.text.toString().toInt()
                    val kg = textInputLayoutKg.editText!!.text.toString().toInt()

                    val zoo = Zoo(type, name, age, kg)
                    mainActivity.animalList.add(zoo)

                    mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)
                }
            }
        }

        return fragmentInputBinding.root
    }
}