package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentPostWriteBinding

class PostWriteFragment : Fragment() {

    lateinit var fragmentPostWriteBinding: FragmentPostWriteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostWriteBinding = FragmentPostWriteBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentPostWriteBinding.run {
            toolbarPostWrtie.run {
                inflateMenu(R.menu.menu_write)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.item_post_write_photo -> {

                        }
                        R.id.item_post_write_album -> {

                        }
                        R.id.item_post_write_save -> {
                            mainActivity.removeFragment(MainActivity.POST_WRITE_FRAGMENT)
                        }
                    }

                    false
                }
            }
        }

        return fragmentPostWriteBinding.root
    }
}