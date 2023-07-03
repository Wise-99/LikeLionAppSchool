package com.test.android_codeviewex

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.test.android_codeviewex.databinding.FragmentShowBinding

class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding
    lateinit var mainActivity: MainActivity

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentShowBinding = FragmentShowBinding.inflate(layoutInflater)

        fragmentShowBinding.run {
            val(name, age, viewList) = mainActivity.personList[mainActivity.personPosition]
            textViewShowName.text = "이름 : $name"
            textViewShowAge.text = "나이 : $age"
            textViewShowHobby.text = "취미 : \n"
            for (v in viewList){
                textViewShowHobby.append("${v.text}\n")
            }

            mainActivity.activityMainBinding.toolbar.run {
                title = "세부 정보 확인"
                setTitleTextColor(Color.WHITE)

                // back 버튼 아이콘을 표시한다.
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                // back 버튼의 아이콘 색상을 변경한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }

                // back 버튼을 누르면 동작하는 리스너
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.FRAGMENT_SHOW)
                    title = "정보 확인"
                    navigationIcon = null
                }
            }
        }

        return fragmentShowBinding.root
    }
}