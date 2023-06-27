package com.test.android_fragmentex02

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.test.android_fragmentex02.databinding.FragmentSwimInputBinding

class SwimInputFragment : Fragment() {

    lateinit var fragmentSwimInputBinding: FragmentSwimInputBinding
    lateinit var mainActivity: MainActivity

    val swimTypeList = arrayOf("자유형", "배영", "접영", "평영")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentSwimInputBinding = FragmentSwimInputBinding.inflate(layoutInflater)

        fragmentSwimInputBinding.run {

            var swimType = ""

            buttonSwimInputType.setOnClickListener {
                val adapter = ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, swimTypeList)

                val builder = AlertDialog.Builder(mainActivity)
                builder.setTitle("리스트 다이얼로그")
                builder.setIcon(R.mipmap.ic_launcher)

                builder.setAdapter(adapter){ dialogInterface: DialogInterface, i: Int ->
                    swimType = swimTypeList[i]
                }

                builder.setNegativeButton("취소", null)
                builder.show()
            }

            val type ="수영부"
            val name = editTextSwimInputName.text.toString()
            val player = Player(type, name, swimType)

            mainActivity.playerList.add(player)

            mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)
        }

        return fragmentSwimInputBinding.root
    }
}