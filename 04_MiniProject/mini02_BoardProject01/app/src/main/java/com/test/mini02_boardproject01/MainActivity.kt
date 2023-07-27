package com.test.mini02_boardproject01

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.test.mini02_boardproject01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    var newFragment : Fragment? = null
    var oldFragment: Fragment? = null

    companion object{
        val LOGIN_FRAGMENT = "LoginFragment"
        val JOIN_FRAGMENT = "JoinFragment"
        val ADD_USER_INFO_FRAGMENT = "AddUserInfoFragment"
        val BOARD_MAIN_FRAGMENT = "BoardMainFragment"
        val POST_LIST_FRAGMEMNT = "PostListFragment"
        val POST_WRITE_FRAGMENT = "PostWriteFragment"
        val MODIFY_USER_FRAGMENT = "ModifyUserFragment"
        val MODIFY_USER_BASIC_FRAGMENT = "ModifyUserBasicFragment"
        val MODIFY_USER_ADDITIONAL_FRAGMENT = "ModifyUserAdditionalFragment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreenCustomizing(splashScreen)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(LOGIN_FRAGMENT, false, null)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:String, addToBackStack:Boolean, bundle:Bundle?){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // newFragment에 Fragment가 들어있으면 oldFragment에 넣어준다.
        if (newFragment != null){
            oldFragment = newFragment
        }

        // 새로운 Fragment를 담을 변수
        var newFragment = when(name){
            LOGIN_FRAGMENT -> LoginFragment()
            JOIN_FRAGMENT -> JoinFragment()
            ADD_USER_INFO_FRAGMENT -> AddUserInfoFragment()
            BOARD_MAIN_FRAGMENT -> BoardMainFragment()
            POST_LIST_FRAGMEMNT -> PostListFragment()
            POST_WRITE_FRAGMENT -> PostWriteFragment()
            MODIFY_USER_FRAGMENT -> ModifyUserFragment()
            MODIFY_USER_BASIC_FRAGMENT -> ModifyUserBasicFragment()
            MODIFY_USER_ADDITIONAL_FRAGMENT -> ModifyUserAdditionalFragment()
            else -> Fragment()
        }

        newFragment?.arguments = bundle

        if(newFragment != null) {

            // oldFragment -> newFragment로 이동
            // oldFramgent : exit
            // newFragment : enter

            // oldFragment <- newFragment 로 되돌아가기
            // oldFragment : reenter
            // newFragment : return

            // 애니메이션 설정
            if (oldFragment != null){
                oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
                oldFragment?.enterTransition = null
                oldFragment?.returnTransition = null
            }

            newFragment?.exitTransition = null
            newFragment?.reenterTransition = null
            newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

            // Fragment를 교체한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment)

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

    // SplashScreen 커스터마이징
    fun splashScreenCustomizing(splashScreen: SplashScreen){
        // SplashScreen이 사라질 때 동작하는 리스너 설정
        splashScreen.setOnExitAnimationListener {
            // 가로 비율 애니메이션
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,1f,2f,1f,0f)
            // 세로 비율 애니메이션
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f, 1f, 0f)
            // 투명도
            val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 1f, 0.5f, 0f)

            // 애니메이션 관리 객체 생성
            // 첫번째 : 애니메이션을 적용할 뷰
            // 나머지는 적용한 애니메이션 종류
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(it.iconView, scaleX, scaleY, alpha)
            // 애니메이션 적용을 위한 에이징
            objectAnimator.interpolator = AnticipateInterpolator()
            // 애니메이션 동작 시간
            objectAnimator.duration = 1000
            // 애니메이션이 끝났을 때 동작할 리스너
            objectAnimator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    // SplashScreen을 제거한다.
                    it.remove()
                }
            })

            // 애니메이션 가동
            objectAnimator.start()
        }
    }
}