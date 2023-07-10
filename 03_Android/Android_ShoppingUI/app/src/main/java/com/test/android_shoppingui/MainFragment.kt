package com.test.android_shoppingui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_shoppingui.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)

        fragmentMainBinding.run {
            buttonMainToLogin.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, false)
            }

            buttonMainToSignUp.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.SIGN_UP_FRAGMENT, false, false)
            }
        }

        return fragmentMainBinding.root
    }
}