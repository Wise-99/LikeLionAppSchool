package com.test.android_sqlitedatabaseex01

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_sqlitedatabaseex01.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    lateinit var fragmentUpdateBinding: FragmentUpdateBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentUpdateBinding = FragmentUpdateBinding.inflate(inflater)

        fragmentUpdateBinding.run {
            toolbarUpdate.run {
                title = "학생 정보 수정"
                setTitleTextColor(Color.WHITE)
            }

            buttonUpdateSave.setOnClickListener {
                var name = ""
                var age = 0
                var korean = 0

                if (editTextUpdateName.text.toString() == ""){
                    name = MainFragment.studentList[mainActivity.rowPosition].name
                } else {
                    name = editTextUpdateName.text.toString()
                }

                if (editTextUpdateAge.text.toString() == ""){
                    age = MainFragment.studentList[mainActivity.rowPosition].age
                } else {
                    age = editTextUpdateAge.text.toString().toInt()
                }

                if (editTextUpdateKorean.text.toString() == ""){
                    korean = MainFragment.studentList[mainActivity.rowPosition].korean
                } else {
                    korean = editTextUpdateKorean.text.toString().toInt()
                }

                val studentClass = StudentClass(mainActivity.rowIdx, name, age, korean)
                DAO.updateData(mainActivity, studentClass, mainActivity.rowIdx)

                mainActivity.removeFragment(MainActivity.UPDATE_FRAGMENT)
                mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
            }
        }

        return fragmentUpdateBinding.root
    }
}