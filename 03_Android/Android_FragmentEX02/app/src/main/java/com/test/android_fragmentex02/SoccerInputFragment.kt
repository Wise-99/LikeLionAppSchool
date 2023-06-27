package com.test.android_fragmentex02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_fragmentex02.databinding.FragmentSoccerInputBinding

class SoccerInputFragment : Fragment() {

    lateinit var fragmentSoccerInputBinding: FragmentSoccerInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity

        fragmentSoccerInputBinding = FragmentSoccerInputBinding.inflate(layoutInflater)

        fragmentSoccerInputBinding.run {
            editTextSoccerInputAssist.setOnEditorActionListener { v, actionId, event ->
                val type = "축구부"
                val name = editTextSoccerInputName.text.toString()
                val goal = editTextSoccerInputGoal.text.toString().toInt()
                val assist = editTextSoccerInputAssist.text.toString().toInt()

                val player = Player(type, name, goal, assist)

                mainActivity.playerList.add(0, player)

                mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)

                false
            }
        }

        return fragmentSoccerInputBinding.root
    }
}