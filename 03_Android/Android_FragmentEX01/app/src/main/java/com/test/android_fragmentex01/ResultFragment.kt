package com.test.android_fragmentex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_fragmentex01.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentResultBinding = FragmentResultBinding.inflate(layoutInflater)

        fragmentResultBinding.run {
            val(type, name, age, kg) = mainActivity.animalList[mainActivity.animalPosition]
            textViewResultType.text = "종류 : $type"
            textViewResultName.text = "이름 : $name"
            textViewResultAge.text= "나이 : $age"
            textViewResultKg.text = "몸무게 : $kg"

            buttonResultBack.setOnClickListener {
                mainActivity.removeFragment(FragmentName.FRAGMENT_RESULT)
            }
        }

        return fragmentResultBinding.root
    }
}