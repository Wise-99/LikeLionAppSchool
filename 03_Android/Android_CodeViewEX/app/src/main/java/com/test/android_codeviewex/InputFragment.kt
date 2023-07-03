package com.test.android_codeviewex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.test.android_codeviewex.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    // 생성된 뷰들을 담을 리스트
    val viewList = mutableListOf<EditText>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)

        fragmentInputBinding.run {
            buttonInputAddHobby.setOnClickListener {

                // View의 가로세로 길이
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // EditText를 생성한다.
                val newEditText = EditText(mainActivity)

                // 설정
                newEditText.layoutParams = params
                newEditText.hint = "취미 추가 입력"

                viewList.add(newEditText)

                // 생성한 뷰를 추가한다.
                fragmentInputBinding.inputContainer.addView(newEditText)
            }

            buttonInputSave.setOnClickListener {
                val name = editTextInputName.text.toString()
                val age = editTextInputAge.text.toString().toInt()
                viewList.add(0, editTextInputHobby)


                val person = Person(name, age, viewList)
                mainActivity.personList.add(person)

                mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)
            }
        }

        return fragmentInputBinding.root
    }
}