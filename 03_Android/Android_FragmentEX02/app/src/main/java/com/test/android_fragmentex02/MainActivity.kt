package com.test.android_fragmentex02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android_fragmentex02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val playerList = mutableListOf<Player>()
    var selectList = mutableListOf<Player>()
    var rowPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(FragmentName.FRAGMENT_MAIN, false, false)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:FragmentName, addToBackStack:Boolean, animate:Boolean){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment: Fragment? = null

        // 이름으로 분기한다.
        when(name){
            FragmentName.FRAGMENT_MAIN -> {
                newFragment = MainFragment()
            }
            FragmentName.FRAGMENT_INPUT -> {
                newFragment = InputFragment()
            }
            FragmentName.FRAGMENT_MODIFY -> {
                newFragment = ModifyFragment()
            }
            FragmentName.FRAGMENT_SHOW -> {
                newFragment = ShowFragment()
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
                fragmentTransaction.addToBackStack(name.str)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:FragmentName){
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}

enum class FragmentName(var str : String){
    FRAGMENT_MAIN("MainFragment"),
    FRAGMENT_INPUT("InputFragment"),
    FRAGMENT_MODIFY("ModifyFragment"),
    FRAGMENT_SHOW("ShowFragment")
}

data class Player(var type:String, var name:String){
    var swimType = ""

    var average = 0F
    var hit = 0
    var homeRun = 0

    var goal = 0
    var assist = 0

    constructor(type:String, name:String, swimType:String) : this(type, name) {
        this.swimType = swimType
    }

    constructor(type:String, name:String, goal:Int, assist:Int) : this(type, name) {
        this.goal = goal
        this.assist = assist
    }

    constructor(type:String, name:String, average:Float, homeRun:Int, hit:Int) : this(type, name){
        this.average = average
        this.hit = hit
        this.homeRun = homeRun
    }
}