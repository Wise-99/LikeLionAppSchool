package com.test.android_sqlitedatabaseex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android_sqlitedatabaseex01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 사용자가 누른 항목 번호
    var rowPosition = 0
    // 사용자가 누른 항목의 idx
    var rowIdx = 0

    companion object {
        // Activity가 관리할 프래그먼트들의 이름
        val MAIN_FRAGMENT = "MainFragment"
        val INPUT_FRAGMENT = "InputFragment"
        val RESULT_FRAGMENT = "ResultFragment"
        val UPDATE_FRAGMENT = "UpdateFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(MAIN_FRAGMENT, false, false)
    }

    fun replaceFragment(name:String, addToBackStack:Boolean, animate:Boolean){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment = when(name){
            MAIN_FRAGMENT -> {
                MainFragment()
            }
            INPUT_FRAGMENT -> {
                InputFragment()
            }
            RESULT_FRAGMENT -> {
                ResultFragment()
            }
            UPDATE_FRAGMENT -> {
                UpdateFragment()
            }
            else -> {
                Fragment()
            }
        }

        if(newFragment != null) {
            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true) {
                // 애니메이션을 설정한다.
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

data class StudentClass(var idx:Int, var name:String, var age:Int, var korean:Int)