package com.test.android_filestreamex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_filestreamex01.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentResultBinding = FragmentResultBinding.inflate(layoutInflater)

        val name = mainActivity.personList[mainActivity.personPosition].name
        val age = mainActivity.personList[mainActivity.personPosition].age
        val korean = mainActivity.personList[mainActivity.personPosition].korean

        fragmentResultBinding.run {

            mainActivity.readObject()

            textViewResult.text = "이름 : ${name}\n"
            textViewResult.append("나이 : ${age}\n")
            textViewResult.append("국어 점수 : $korean")
        }

        return fragmentResultBinding.root
    }
}