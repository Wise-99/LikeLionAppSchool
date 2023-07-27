package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentModifyUserBinding

class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentModifyUserBinding = FragmentModifyUserBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentModifyUserBinding.run {
            toolbarModify.run {
                title = "사용자 정보 수정"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MODIFY_USER_FRAGMENT)
                }
            }

            // 기본 정보 수정 버튼
            buttonModifyUserBasic.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MODIFY_USER_BASIC_FRAGMENT, true, null)
            }

            // 추가 정보 수정 버튼
            buttonModifyUserAdditional.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.MODIFY_USER_ADDITIONAL_FRAGMENT,true, null)
            }
        }

        return fragmentModifyUserBinding.root
    }
}