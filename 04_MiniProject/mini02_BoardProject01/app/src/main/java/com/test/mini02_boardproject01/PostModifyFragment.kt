package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentPostModifyBinding

class PostModifyFragment : Fragment() {

    lateinit var fragmentPostModifyBinding: FragmentPostModifyBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostModifyBinding = FragmentPostModifyBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentPostModifyBinding.run {
            toolbarPostModify.run {
                title = "게시글 수정"
                inflateMenu(R.menu.menu_post_modify)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.item_post_modify_camera -> {

                        }
                        R.id.item_post_modify_album -> {

                        }
                        R.id.item_post_modify_done -> {
                            val title = textInputEditTextModifyTitle.text.toString()
                            val content =textInputEditTextModifyContent.text.toString()
                            if(title != "" && content != ""){
                                mainActivity.removeFragment(MainActivity.POST_MODIFY_FRAGMENT)
                            } else {
                                textInputLayoutModifyTitle.error = "입력 확인해주세요!"
                            }
                        }
                    }
                    true
                }

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.POST_MODIFY_FRAGMENT)
                }
            }


        }

        return fragmentPostModifyBinding.root
    }
}