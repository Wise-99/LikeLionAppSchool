package com.test.mini02_boardproject01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject01.databinding.FragmentPostListBinding

class PostListFragment : Fragment() {

    lateinit var fragmentPostListBinding: FragmentPostListBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostListBinding = FragmentPostListBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentPostListBinding.run {
            searchBar.run {
                setOnClickListener {
                    searchView.requestFocus()
                }
            }

            searchView.run {
                inflateMenu(R.menu.menu_post_list)
            }
        }



        return fragmentPostListBinding.root
    }
}