package com.test.mini02_boardproject01

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.test.mini02_boardproject01.databinding.FragmentAddUserInfoBinding
import kotlin.concurrent.thread

class AddUserInfoFragment : Fragment() {

    lateinit var fragmentAddUserInfoBinding: FragmentAddUserInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddUserInfoBinding = FragmentAddUserInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAddUserInfoBinding.run {

            textInputEditTextAddUserInfoNickName.requestFocus()

            thread {
                SystemClock.sleep(500)

                val imm = mainActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(mainActivity.currentFocus, 0)
            }

            // toolbar
            toolbarAddUserInfo.run{
                title = "회원가입"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ADD_USER_INFO_FRAGMENT)
                }
            }

            // 가입 완료 버튼
            buttonAddUserInfoSubmit.run {
                setOnClickListener {
                    if (textInputEditTextAddUserInfoNickName.text.toString() == ""){
                        textInputLayoutAddUserInfoNickName.error = "닉네임 오류"
                    }

                    else if (textInputEditTextAddUserInfoAge.text.toString() == ""){
                        textInputLayoutAddUserInfoNickName.error = ""
                        textInputLayoutAddUserInfoAge.error = "나이 입력 오류"
                    }

                    else {
                        mainActivity.removeFragment(MainActivity.ADD_USER_INFO_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
                    }
                }
            }
        }

        return fragmentAddUserInfoBinding.root
    }
}