package com.test.android_fragmentex02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_fragmentex02.databinding.FragmentShowBinding

class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentShowBinding = FragmentShowBinding.inflate(layoutInflater)

        fragmentShowBinding.run {
            var text = "이름 : ${mainActivity.selectList[mainActivity.rowPosition].name}\n"
            val type = "${mainActivity.selectList[mainActivity.rowPosition].type}"
            text = text + type + "\n"
            when(type){
                "야구부" -> {
                    text += "타율 : ${mainActivity.selectList[mainActivity.rowPosition].average}\n"
                    text += "홈런 수 : ${mainActivity.selectList[mainActivity.rowPosition].homeRun}\n"
                    text += "안타 수 : ${mainActivity.selectList[mainActivity.rowPosition].hit}\n"

                }
                "축구부" -> {
                    text += "골 수 : ${mainActivity.selectList[mainActivity.rowPosition].goal}\n"
                    text += "도움 수 : ${mainActivity.selectList[mainActivity.rowPosition].assist}"
                }
                "수영부" -> {
                    text += "수영법 : ${mainActivity.selectList[mainActivity.rowPosition].swimType}"
                }
            }
            textViewShow.text = text

            buttonShowToDelete.setOnClickListener {
                mainActivity.playerList.remove(mainActivity.selectList[mainActivity.rowPosition])
                mainActivity.removeFragment(FragmentName.FRAGMENT_SHOW)
            }

            buttonShowToModify.setOnClickListener {

            }
        }

        return fragmentShowBinding.root
    }
}