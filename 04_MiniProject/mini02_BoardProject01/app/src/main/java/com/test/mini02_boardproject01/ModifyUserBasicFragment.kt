package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentModifyUserBasicBinding

class ModifyUserBasicFragment : Fragment() {

    lateinit var fragmentModifyUserBasicBinding: FragmentModifyUserBasicBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentModifyUserBasicBinding = FragmentModifyUserBasicBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentModifyUserBasicBinding.run {
            toolbarModifyBasic.run {
                title = "기본 사용자 정보 수정"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.MODIFY_USER_BASIC_FRAGMENT)
                }
            }
        }

        return fragmentModifyUserBasicBinding.root
    }
}