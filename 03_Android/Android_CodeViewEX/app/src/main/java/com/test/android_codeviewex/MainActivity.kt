package com.test.android_codeviewex

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android_codeviewex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    val personList = mutableListOf<Person>()
    var personPosition = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            toolbar.run {
                title = "정보 확인"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.main_menu)

                setOnMenuItemClickListener {
                    when(it?.itemId){
                        R.id.menuAdd -> {
                            replaceFragment(FragmentName.FRAGMENT_INPUT, true, true)

                            toolbar.title = "정보 입력"

                            // back 버튼 아이콘을 표시한다.
                            setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                            // back 버튼의 아이콘 색상을 변경한다.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                            } else {
                                navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                            }

                            // back 버튼을 누르면 동작하는 리스너
                            setNavigationOnClickListener {
                                removeFragment(FragmentName.FRAGMENT_INPUT)
                                toolbar.title = "정보 확인"
                                toolbar.navigationIcon = null
                            }
                        }
                    }
                    false
                }
            }
        }

        replaceFragment(FragmentName.FRAGMENT_MAIN,false,false)
    }

    fun replaceFragment(name:FragmentName, addToBackStack : Boolean, animate:Boolean){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment: Fragment? = null

        // 이름으로 분기한다.
        when(name){
            FragmentName.FRAGMENT_MAIN->{
                // Framgnet 객체를 생성한다.
                newFragment = MainFragment()
            }
            FragmentName.FRAGMENT_INPUT -> {
                newFragment = InputFragment()
            }
            FragmentName.FRAGMENT_SHOW -> {
                newFragment = ShowFragment()
            }
        }

        if (newFragment != null){
            //MainFragment가 보여지도록 Fragment를 교체한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

            if (animate == true){
                // 애니메이션을 설정한다.
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true){
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

// fargment들을 구분하기 위한 값
enum class FragmentName(val str:String){
    FRAGMENT_MAIN("MainFragment"),
    FRAGMENT_INPUT("InputFragment"),
    FRAGMENT_SHOW("ShowFragment")
}

data class Person(var name:String, var age:Int, var hobbyList:MutableList<EditText>)