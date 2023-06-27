package com.test.android_fragmentex02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.test.android_fragmentex02.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    val selectInputList = arrayOf("야구부", "축구부", "수영부")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)

        fragmentInputBinding.run {
            spinnerInput.run {
                val spinnerInputAdapter = ArrayAdapter<String>(
                    mainActivity, android.R.layout.simple_spinner_item, selectInputList
                )
                spinnerInputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = spinnerInputAdapter

                setSelection(0)

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when(position){
                            0 -> {
                                replaceInputFragment(InputFragmentName.FRAGMENT_BASEBALL)
                            }
                            1 -> {
                                replaceInputFragment(InputFragmentName.FRAGMENT_SOCCER)
                            }
                            2 -> {
                                replaceInputFragment(InputFragmentName.FRAGMENT_SWIM)
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
            }
        }

        return fragmentInputBinding.root
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceInputFragment(name:InputFragmentName){
        // Fragment 교체 상태로 설정한다.
        val inputFragmentTransaction = childFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment: Fragment? = null

        // 이름으로 분기한다.
        when(name){
            InputFragmentName.FRAGMENT_BASEBALL -> {
                newFragment = BaseballInputFragment()
                removeFragment(InputFragmentName.FRAGMENT_SOCCER)
                removeFragment(InputFragmentName.FRAGMENT_SWIM)
            }
            InputFragmentName.FRAGMENT_SOCCER -> {
                newFragment = SoccerInputFragment()
                removeFragment(InputFragmentName.FRAGMENT_BASEBALL)
                removeFragment(InputFragmentName.FRAGMENT_SWIM)
            }
            InputFragmentName.FRAGMENT_SWIM -> {
                newFragment = SwimInputFragment()
                removeFragment(InputFragmentName.FRAGMENT_BASEBALL)
                removeFragment(InputFragmentName.FRAGMENT_SOCCER)
            }
        }

        if(newFragment != null) {
            // Fragment를 교채한다.
            inputFragmentTransaction.replace(R.id.inputContainer, newFragment)

            // 교체 명령이 동작하도록 한다.
            inputFragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:InputFragmentName){
        childFragmentManager.popBackStack(name.inputStr, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

enum class InputFragmentName(var inputStr : String){
    FRAGMENT_BASEBALL("BaseballInputFragment"),
    FRAGMENT_SOCCER("SoccerInputFragment"),
    FRAGMENT_SWIM("SwimInputFragment")
}