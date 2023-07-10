package com.test.android_shoppingui

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_shoppingui.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    lateinit var fragmentSplashBinding: FragmentSplashBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentSplashBinding.run {
            SystemClock.sleep(3000)
            mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, false, false)
        }

        return fragmentSplashBinding.root
    }
}