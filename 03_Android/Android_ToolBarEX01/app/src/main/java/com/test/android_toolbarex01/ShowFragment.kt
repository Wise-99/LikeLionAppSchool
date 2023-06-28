package com.test.android_toolbarex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_toolbarex01.databinding.FragmentShowBinding

class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentShowBinding = FragmentShowBinding.inflate(layoutInflater)

        val(name, age, korean) = mainActivity.studentList[mainActivity.rowPosition]

        fragmentShowBinding.run {
            textViewShowName.text = "이름 : $name"
            textViewShowAge.text = "나이 : $age"
            textViewShowKorean.text = "국어 점수 : $korean"
        }

        return fragmentShowBinding.root
    }
}