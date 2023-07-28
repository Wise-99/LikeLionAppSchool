package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentModifyUserBinding

class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentModifyUserBinding = FragmentModifyUserBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentModifyUserBinding.run {

            buttonModifyUserAccept.setOnClickListener {
                val pw1 = textInputEditTextModifyUserPw.text.toString()
                val pw2 = textInputEditTextModifyUserPw2.text.toString()

                if (pw1 != pw2){
                    textInputEditTextModifyUserPw.error = "비밀번호 오류"
                } else if (textInputEditTextModifyUserInfoNickName.text.toString() == ""){
                    textInputEditTextModifyUserPw.error = ""
                    textInputLayoutModifyUserInfoNickName.error = "닉네임 입력 오류"
                } else if (textInputEditTextModifyUserInfoAge.text.toString() == ""){
                    textInputEditTextModifyUserPw.error = ""
                    textInputLayoutModifyUserInfoNickName.error = ""
                    textInputEditTextModifyUserInfoAge.error = "나이 입력 오류"
                } else {
                    mainActivity.removeFragment(MainActivity.MODIFY_USER_FRAGMENT)
                }
            }
        }

        return fragmentModifyUserBinding.root
    }
}