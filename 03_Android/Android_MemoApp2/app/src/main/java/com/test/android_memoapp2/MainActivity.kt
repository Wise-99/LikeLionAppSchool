package com.test.android_memoapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android_memoapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    companion object {
        // Activity가 관리할 프래그먼트들의 이름
        val LOGIN_FRAGMENT = "LoginFragment"
        val ADD_MEMO_FRAGMENT = "AddMemoFragment"
        val PASSWORD_FRAGMENT = "PasswordFragment"
        val RESULT_MEMO_FRAMGENT = "ResultMemoFragment"
        val SHOW_CATEGORY_FRAGMENT = "ShowCategoryFragment"
        val SHOW_MEMO_FRAGMENT = "ShowMemoFragment"
        val UPDATE_MEMO_FRAGMENT = "UpdateMemoFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        if (DAO.tableExist(this)){
            replaceFragment(LOGIN_FRAGMENT, false, false)
        } else {
            replaceFragment(PASSWORD_FRAGMENT, false, false)
        }
    }

    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean){
        // Fragment 교체 상태로 설정
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment = when(name){
            LOGIN_FRAGMENT -> {
                LoginFragment()
            }
            ADD_MEMO_FRAGMENT -> {
                AddMemoFragment()
            }
            PASSWORD_FRAGMENT -> {
                PasswordFragment()
            }
            RESULT_MEMO_FRAMGENT -> {
                ResultMemoFragment()
            }
            SHOW_CATEGORY_FRAGMENT -> {
                ShowCategoryFragment()
            }
            SHOW_MEMO_FRAGMENT -> {
                ShowMemoFragment()
            }
            UPDATE_MEMO_FRAGMENT -> {
                UpdateMemoFragment()
            }
            else -> {
                Fragment()
            }
        }

        if(newFragment != null) {
            // Fragment 교체
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true) {
                // 애니메이션 설정
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거
    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}