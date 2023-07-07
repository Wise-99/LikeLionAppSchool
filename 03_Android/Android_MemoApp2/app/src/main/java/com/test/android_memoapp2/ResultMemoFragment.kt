package com.test.android_memoapp2

import android.content.DialogInterface
import android.content.res.ColorStateList
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
import androidx.appcompat.app.AlertDialog
import com.test.android_memoapp2.databinding.FragmentResultMemoBinding

class ResultMemoFragment : Fragment() {

    lateinit var fragmentResultMemoBinding: FragmentResultMemoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentResultMemoBinding = FragmentResultMemoBinding.inflate(inflater)

        // 메모 정보 불러오기
        val memo = DAO.selectMemo(mainActivity, ShowMemoFragment.rowMemoIdx, ShowMemoFragment.category)
        val(mIdx, mTitle, mContent, mDate) = memo

        fragmentResultMemoBinding.run {
            // 메모 정보 화면에 표시
            textViewResultMemoTitle.text = mTitle
            textViewResultMemoContent.text = mContent
            textViewResultMemoDate.text = mDate

            toolbarResultMemo.run {
                title = "메모 보기"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.result_memo_menu)

                setOnMenuItemClickListener{
                    when(it.itemId){
                        // 수정 버튼
                        R.id.resultMemoUpdate -> {
                            mainActivity.replaceFragment(MainActivity.UPDATE_MEMO_FRAGMENT, true, true)
                        }

                        // 삭제 버튼
                        R.id.resultMemoDelete -> {
                            DAO.deleteMemo(mainActivity, mIdx, ShowMemoFragment.category)
                            mainActivity.removeFragment(MainActivity.RESULT_MEMO_FRAMGENT)
                        }
                    }

                    false
                }

                // back 버튼 아이콘 표시
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                // back 버튼 아이콘 색상 변경
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    navigationIcon?.colorFilter =
                        BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }

                // back 버튼 리스너
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.RESULT_MEMO_FRAMGENT)
                }
            }
        }

        return fragmentResultMemoBinding.root
    }
}