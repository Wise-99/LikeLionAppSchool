package com.test.android_memoapp01

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
import com.test.android_memoapp01.databinding.FragmentAddBinding
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class AddFragment : Fragment() {

    lateinit var fragmentAddBinding: FragmentAddBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentAddBinding = FragmentAddBinding.inflate(inflater)

        val imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentAddBinding.run {

            editTextAddTitle.requestFocus()
            thread {
                SystemClock.sleep(100)
                imm.showSoftInput(editTextAddTitle, 0)
            }

            toolbarAdd.run {
                title = "메모 추가"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.add_menu)

                // 저장 버튼 클릭 시
                setOnMenuItemClickListener {
                    val title = editTextAddTitle.text.toString()
                    val content = editTextAddContent.text.toString()

                    // 현재 시간 구하기
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val now = sdf.format(Date())

                    // 메모 객체 생성
                    val memo = MemoClass(0, title, content, now)

                    // 메모 저장
                    DAO.insertData(mainActivity, memo)

                    mainActivity.removeFragment(MainActivity.ADD_FRAGMENT)
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
                    mainActivity.removeFragment(MainActivity.ADD_FRAGMENT)
                }
            }
        }

        return fragmentAddBinding.root
    }
}