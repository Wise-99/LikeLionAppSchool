package com.test.android_fragmentex02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_fragmentex02.databinding.FragmentBaseballInputBinding

class BaseballInputFragment : Fragment() {

    lateinit var fragmentBaseballInputBinding: FragmentBaseballInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentBaseballInputBinding = FragmentBaseballInputBinding.inflate(layoutInflater)

        fragmentBaseballInputBinding.run {
            editTextBaseballInputHit.setOnEditorActionListener { v, actionId, event ->
                val type = "야구부"
                val name = editTextBaseballInputName.text.toString()
                val average = editTextBaseballInputAverage.text.toString().toFloat()
                val homeRun = editTextBaseballInputHomeRun.text.toString().toInt()
                val hit = editTextBaseballInputHit.text.toString().toInt()

                val player = Player(type, name, average, homeRun, hit)
                mainActivity.playerList.add(0, player)

                mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)

                false
            }
        }

        return fragmentBaseballInputBinding.root
    }
}