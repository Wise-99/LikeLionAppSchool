package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentPostReadBinding

class PostReadFragment : Fragment() {

    lateinit var fragmentPostReadBinding: FragmentPostReadBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostReadBinding = FragmentPostReadBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentPostReadBinding.run {
            toolbarPostRead.run {
                inflateMenu(R.menu.menu_post_read)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.item_post_read_update -> {
                            mainActivity.replaceFragment(MainActivity.POST_MODIFY_FRAGMENT, true, null)
                        }
                        R.id.item_post_read_delete -> {
                            mainActivity.removeFragment(MainActivity.POST_READ_FRAGMENT)
                        }
                    }

                    false
                }
            }
        }

        return fragmentPostReadBinding.root
    }
}