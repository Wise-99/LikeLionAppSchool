package com.test.mini02_boardproject01

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.test.mini02_boardproject01.databinding.FragmentJoinBinding
import kotlin.concurrent.thread

class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentJoinBinding = FragmentJoinBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentJoinBinding.run{

            textInputEditTextJoinUserId.requestFocus()

            thread {
                SystemClock.sleep(500)

                val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(mainActivity.currentFocus, 0)
            }

            // toolbar
            toolbarJoin.run{
                title = "회원가입"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
                }
            }
            // 다음 버튼
            buttonJoinNext.run{
                setOnClickListener {
                    val id = textInputEditTextJoinUserId.text.toString()
                    val pw1 = textInputEditTextJoinUserPw.text.toString()
                    val pw2 = textInputEditTextJoinUserPw2.text.toString()

                    if (id != "" && pw1 != "" && pw2 != ""){
                        if(pw1 == pw2){
                            mainActivity.replaceFragment(MainActivity.ADD_USER_INFO_FRAGMENT, true, null)
                        }
                    }
                }
            }
        }

        return fragmentJoinBinding.root
    }

}