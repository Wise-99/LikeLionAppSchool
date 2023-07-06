package com.test.android_memoapp01

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.test.android_memoapp01.databinding.FragmentUpdateBinding
import kotlin.concurrent.thread

class UpdateFragment : Fragment() {

    lateinit var fragmentUpdateBinding: FragmentUpdateBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentUpdateBinding = FragmentUpdateBinding.inflate(inflater)

        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentUpdateBinding.run {

            editTextUpdateTitle.requestFocus()
            thread {
                SystemClock.sleep(100)
                imm.showSoftInput(editTextUpdateTitle, 0)
            }

            toolbarUpdate.run {
                title = "메모 수정"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.add_menu) // toolbarAdd와 같은 기능이므로 xml 재사용

                setOnMenuItemClickListener {
                    var title = ""
                    var content = ""

                    // 제목 입력 안했을 때
                    if (editTextUpdateTitle.text.toString() == ""){
                        title = MainFragment.memoList[MainFragment.rowPosition].title
                    }
                    // 제목 입력했을 때
                    else {
                        title = editTextUpdateTitle.text.toString()
                    }

                    // 내용 입력 안했을 때
                    if (editTextUpdateContent.text.toString() == ""){
                        content = MainFragment.memoList[MainFragment.rowPosition].content
                    }
                    // 내용 입력했을 때
                    else {
                        content = editTextUpdateContent.text.toString()
                    }
                    // 날짜는 저장되어있던 날짜를 다시 가져온다.
                    val date = MainFragment.memoList[MainFragment.rowPosition].date

                    // 메모 객체 생성 및 수정
                    val memo = MemoClass(MainFragment.rowIdx, title, content, date)
                    DAO.updateData(mainActivity, memo, MainFragment.rowIdx)

                    mainActivity.removeFragment(MainActivity.UPDATE_FRAGMENT)
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
                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                }
            }
        }

        return fragmentUpdateBinding.root
    }
}