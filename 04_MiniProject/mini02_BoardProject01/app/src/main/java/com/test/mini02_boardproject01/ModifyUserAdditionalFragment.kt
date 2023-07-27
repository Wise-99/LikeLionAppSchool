package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentModifyUserAdditionalBinding

class ModifyUserAdditionalFragment : Fragment() {

    lateinit var fragmentModifyUserAdditionalBinding: FragmentModifyUserAdditionalBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentModifyUserAdditionalBinding = FragmentModifyUserAdditionalBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentModifyUserAdditionalBinding.run {
            toolbarModifyUserInfo.run {
                title = "추가 사용자 정보 수정"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MODIFY_USER_ADDITIONAL_FRAGMENT)
                }
            }
        }

        return fragmentModifyUserAdditionalBinding.root
    }
}