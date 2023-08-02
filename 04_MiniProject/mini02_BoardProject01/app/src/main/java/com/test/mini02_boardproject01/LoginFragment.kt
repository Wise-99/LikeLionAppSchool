package com.test.mini02_boardproject01

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.test.mini02_boardproject01.databinding.FragmentLoginBinding
import com.test.mini02_boardproject01.repository.UserRepository

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
            buttonLoginSubmit.run{
                setOnClickListener {
                    loginSubmit()

                }
            }

            // 비빌번호 입력요소
            textInputEditTextLoginUserPw.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    loginSubmit()
                    true
                }
            }
        }

        return fragmentLoginBinding.root
    }

    // 로그인 버튼을 눌렀거나 비밀번호 입력요소에서 엔터키를 눌렀을 때...
    fun loginSubmit(){
        fragmentLoginBinding.run{
            // 사용자가 입력한 내용을 가져온다.
            val loginUserId = textInputEditTextLoginUserId.text.toString()
            val loginUserPw = textInputEditTextLoginUserPw.text.toString()

            if(loginUserId.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserId)
                }
                builder.show()
                return
            }

            if(loginUserPw.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserPw)
                }
                builder.show()
                return
            }

            UserRepository.login(mainActivity, loginUserId, loginUserPw, fragmentLoginBinding)
        }
    }
}