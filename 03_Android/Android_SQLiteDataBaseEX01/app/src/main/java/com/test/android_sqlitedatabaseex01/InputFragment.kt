package com.test.android_sqlitedatabaseex01

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_sqlitedatabaseex01.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(inflater)

        fragmentInputBinding.run {
            toolbarInput.run {
                title = "학생 정보 입력"
                setTitleTextColor(Color.WHITE)
            }

            editTextInputKorean.setOnEditorActionListener { v, actionId, event ->
                val name = editTextInputName.text.toString()
                val age = editTextInputAge.text.toString().toInt()
                val korean = editTextInputKorean.text.toString().toInt()

                // 객체 생성
                val studentClass = StudentClass(0, name, age, korean)

                DAO.insertData(mainActivity, studentClass)
                mainActivity.removeFragment(MainActivity.INPUT_FRAGMENT)

                false
            }
        }

        return fragmentInputBinding.root
    }
}