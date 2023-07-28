package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentLoginBinding.run{
            // toolbar
            toolbarLogin.run{
                title = "로그인"
            }

            textInputEditTextLoginUserPw.run {
                setOnEditorActionListener { v, actionId, event ->
                    checkIdPw()

                    true
                }
            }

            // 회원가입 버튼
            buttonLoginJoin.run{
                setOnClickListener {
                    // JoinFragment를 보이게 한다.
                    mainActivity.replaceFragment(MainActivity.JOIN_FRAGMENT, true, null)
                }
            }

            // 로그인 버튼
            buttonLoginSubmit.run {
                setOnClickListener {
                    checkIdPw()
                }
            }
        }

        return fragmentLoginBinding.root
    }

    fun checkIdPw(){
        if(fragmentLoginBinding.textInputEditTextLoginUserId.text.toString() == ""){
            fragmentLoginBinding.textInputLayoutLoginUserId.error = "아이디 오류"
        }

        else if (fragmentLoginBinding.textInputEditTextLoginUserPw.text.toString() == ""){
            fragmentLoginBinding.textInputLayoutLoginUserId.error = ""
            fragmentLoginBinding.textInputLayoutLoginUserPw.error = "비밀번호 오류"
        }

        else {
            mainActivity.replaceFragment(MainActivity.BOARD_MAIN_FRAGMENT, false, null)
        }
    }
}