package com.test.android_shoppingui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_shoppingui.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var fragmentSignUpBinding: FragmentSignUpBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentSignUpBinding.run {
            editTextPassword2.setOnEditorActionListener { v, actionId, event ->
                mainActivity.replaceFragment(MainActivity.PRODUCT_FRAGMENT, true, true)
                false
            }
        }

        return fragmentSignUpBinding.root
    }
}