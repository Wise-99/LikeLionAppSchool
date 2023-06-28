package com.test.android_toolbarex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.android_toolbarex01.databinding.FragmentInputBinding
import com.test.android_toolbarex01.databinding.RowBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)

        fragmentInputBinding.run {
            editTextInputKorean.setOnEditorActionListener { v, actionId, event ->
                val name = editTextInputName.text.toString()
                val age = editTextInputAge.text.toString().toInt()
                val korean = editTextInputKorean.text.toString().toInt()

                val student = Student(name, age, korean)
                mainActivity.studentList.add(0, student)

                mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)

                false
            }
        }

        return fragmentInputBinding.root
    }
}