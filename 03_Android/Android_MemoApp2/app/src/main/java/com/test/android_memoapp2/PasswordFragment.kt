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
import com.test.android_memoapp2.databinding.FragmentPasswordBinding
import kotlin.concurrent.thread

class PasswordFragment : Fragment() {

    lateinit var fragmentPasswordBinding: FragmentPasswordBinding
    lateinit var mainActivity: MainActivity
    lateinit var snackBar : Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentPasswordBinding = FragmentPasswordBinding.inflate(inflater)

        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentPasswordBinding.run {

            toolbarPassword.run {
                title = "비밀번호 설정"
                setTitleTextColor(Color.WHITE)
            }

            // 포커스 설정
            editTextSetPassword.requestFocus()
            // 키보드 올림
            thread {
                SystemClock.sleep(1000)
                imm.showSoftInput(mainActivity.currentFocus, 0)
            }

            // 비밀번호 설정 완료 버튼 클릭 시
            buttonSetPassword.setOnClickListener {
                // 입력한 비밀번호 2개의 값 받기
                val password1 = editTextSetPassword.text.toString()
                val password2 = editTextSetPassword2.text.toString()

                // 입력한 비밀번호가 일치하면 저장
                if (password1 == password2 && password1 != ""){
                    DAO.insertPassword(mainActivity, password1)
                    snackBar = Snackbar.make(it, "비밀번호 저장 완료!", Snackbar.LENGTH_LONG)
                    mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, false)
                }
                // 틀렸을 때
                else {
                    snackBar = Snackbar.make(it, "입력한 비밀번호를 확인해주세요.", Snackbar.LENGTH_LONG)
                }

                // 키보드 숨김
                imm.hideSoftInputFromWindow(mainActivity.currentFocus!!.windowToken, 0)
                mainActivity.currentFocus!!.clearFocus()

                snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                snackBar.show()
            }

        }

        return fragmentPasswordBinding.root
    }
}