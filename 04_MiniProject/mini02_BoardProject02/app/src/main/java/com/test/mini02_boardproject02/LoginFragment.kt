package com.test.mini02_boardproject02

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.test.mini02_boardproject02.databinding.FragmentLoginBinding
import kotlin.concurrent.thread

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
                    loginSubmit()
                    // mainActivity.replaceFragment(MainActivity.BOARD_MAIN_FRAGMENT, false, null)
                }
            }

            // 비밀번호 입력 요소
            textInputEditTextLoginUserPw.run {
                setOnEditorActionListener { v, actionId, event ->
                    loginSubmit()
                    true
                }
            }
        }

        return fragmentLoginBinding.root
    }

    // 로그인 버튼을 눌렀거나 비밀번호 입력 요소에서 엔터 키를 눌렀을 때
    fun loginSubmit(){
        fragmentLoginBinding.run {
            // 사용자가 입력한 내용을 가져온다.
            val loginUserId = textInputEditTextLoginUserId.text.toString()
            val loginUserPw = textInputEditTextLoginUserPw.text.toString()

            if (loginUserId.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

                    mainActivity.showSoftInput(textInputEditTextLoginUserId)
                }
                builder.show()
                return
            }

            if (loginUserPw.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

                    mainActivity.showSoftInput(textInputEditTextLoginUserPw)
                }
                builder.show()
                return
            }

            mainActivity.replaceFragment(MainActivity.BOARD_MAIN_FRAGMENT, false, null)
        }
    }
}