package com.test.android69_ex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android69_ex01.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    lateinit var fragmentAddBinding: FragmentAddBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddBinding = FragmentAddBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAddBinding.run {
            editTextAddKorean.setOnEditorActionListener { v, actionId, event ->
                val name = editTextAddName.text.toString()
                val age = editTextAddAge.text.toString().toInt()
                val korean = editTextAddKorean.text.toString().toInt()

                val studentClass = StudentClass(name, age, korean)

                // 저장된 데이터들을 불러온다.
                val studentCount = mainActivity.getStudentCount()
                val studentList = mainActivity.getStudentInfo(studentCount)

                // 학생 정보를 저장한다.
                studentList.add(studentClass)
                mainActivity.writeStudentCount(studentCount+1)
                mainActivity.addStudentInfo(studentList)

                mainActivity.removeFragment(MainActivity.ADD_FRAGMENT)

                false
            }
        }

        return fragmentAddBinding.root
    }
}