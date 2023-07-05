package com.test.android_sqlitedatabaseex01

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_sqlitedatabaseex01.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentResultBinding = FragmentResultBinding.inflate(inflater)

        fragmentResultBinding.run {

            toolbarResult.run {
                title = "학생 정보 확인"
                setTitleTextColor(Color.WHITE)
            }

            val student = DAO.selectData(mainActivity, mainActivity.rowIdx)
            val (idx, name, age, korean) = student

            textViewResultName.text = "이름 : $name"
            textViewResultAge.text = "나이 : $age"
            textViewResultKorean.text = "국어 점수 : $korean"

            buttonResultToUpdate.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.UPDATE_FRAGMENT, true, true)
            }

            buttonResultToDelete.setOnClickListener {
                DAO.deleteData(mainActivity, mainActivity.rowIdx)
                mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
            }
        }

        return fragmentResultBinding.root
    }
}