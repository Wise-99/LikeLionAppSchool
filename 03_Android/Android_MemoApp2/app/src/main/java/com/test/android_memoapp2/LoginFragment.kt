package com.test.android_memoapp2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.test.android_memoapp2.databinding.FragmentLoginBinding
import kotlin.concurrent.thread

class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity
    lateinit var snackBar : Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)

        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentLoginBinding.run {

            toolbarLogin.run {
                title = "로그인"
                setTitleTextColor(Color.WHITE)
            }

            // 포커스 설정
            editTextLoginPassword.requestFocus()
            // 키보드 올림
            thread {
                SystemClock.sleep(1000)
                imm.showSoftInput(mainActivity.currentFocus, 0)
            }

            buttonLogin.setOnClickListener {
                val pwd = editTextLoginPassword.text.toString()

                if (DAO.comparePassword(mainActivity, pwd)){
                    snackBar = Snackbar.make(it, "로그인 성공!", Snackbar.LENGTH_LONG)
                    mainActivity.replaceFragment(MainActivity.SHOW_CATEGORY_FRAGMENT, false, false)
                } else {
                    snackBar = Snackbar.make(it, "입력한 비밀번호를 확인해주세요.", Snackbar.LENGTH_LONG)
                }

                // 키보드 숨김
                imm.hideSoftInputFromWindow(mainActivity.currentFocus!!.windowToken, 0)
                mainActivity.currentFocus!!.clearFocus()

                snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                snackBar.show()
            }
        }

        return fragmentLoginBinding.root
    }
}