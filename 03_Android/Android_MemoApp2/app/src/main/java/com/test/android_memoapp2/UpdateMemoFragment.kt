package com.test.android_memoapp2

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.test.android_memoapp2.databinding.FragmentUpdateMemoBinding
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class UpdateMemoFragment : Fragment() {

    lateinit var fragmentUpdateMemoBinding: FragmentUpdateMemoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentUpdateMemoBinding = FragmentUpdateMemoBinding.inflate(inflater)

        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentUpdateMemoBinding.run {
            // 수정 전 제목과 내용 표시
            editTextUpdateMemoTitle.setText("${ShowMemoFragment.memoList[ShowMemoFragment.rowMemoPosition].title}")
            editTextUpdateMemoContent.setText("${ShowMemoFragment.memoList[ShowMemoFragment.rowMemoPosition].content}")

            // 포커스 설정
            editTextUpdateMemoTitle.requestFocus()
            // 키보드 올림
            thread {
                SystemClock.sleep(1000)
                imm.showSoftInput(editTextUpdateMemoTitle, 0)
            }

            toolbarUpdateMemo.run {
                title = "메모 수정"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.save_memo)

                // 저장 버튼 클릭 시
                setOnMenuItemClickListener {
                    val title = editTextUpdateMemoTitle.text.toString()
                    val content = editTextUpdateMemoContent.text.toString()

                    //  저장 날짜 구하기
                    val date = ShowMemoFragment.memoList[ShowMemoFragment.rowMemoPosition].date

                    // 메모 객체 생성
                    val memo = MemoClass(0, title, content, date)

                    // 메모 수정
                    DAO.updateMemo(mainActivity, memo, ShowMemoFragment.rowMemoIdx, ShowMemoFragment.category)

                    mainActivity.removeFragment(MainActivity.UPDATE_MEMO_FRAGMENT)
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
                    mainActivity.removeFragment(MainActivity.UPDATE_MEMO_FRAGMENT)
                }
            }
        }

        return fragmentUpdateMemoBinding.root
    }
}