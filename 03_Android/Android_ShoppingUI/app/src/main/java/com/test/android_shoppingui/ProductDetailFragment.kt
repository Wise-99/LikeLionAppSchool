package com.test.android_shoppingui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.android_shoppingui.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentProductDetailBinding.run {
            toolbarProductDetail.run {
                inflateMenu(R.menu.product_detail_menu)

                // back 버튼 아이콘 표시
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                // back 버튼 리스너
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.PRODUCT_DETAIL_FRAGMENT)
                }
            }
        }

        return fragmentProductDetailBinding.root
    }
}