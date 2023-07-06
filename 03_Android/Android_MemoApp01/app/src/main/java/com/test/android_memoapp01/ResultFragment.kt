package com.test.android_memoapp01

import android.content.DialogInterface
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
import com.test.android_memoapp01.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentResultBinding = FragmentResultBinding.inflate(inflater)

        fragmentResultBinding.run {
            toolbarResult.run {
                title = "메모 읽기"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.result_menu)

                setOnMenuItemClickListener{
                    when(it.itemId){
                        // 수정 버튼
                        R.id.resultUIpdate -> {
                            mainActivity.replaceFragment(MainActivity.UPDATE_FRAGMENT, true, true)
                        }

                        // 삭제 버튼
                        R.id.resultDelete -> {
                            // 다이얼로그 생성을 위한 객체 생성
                            val builder = AlertDialog.Builder(mainActivity)
                            // 타이틀
                            builder.setTitle("메모 삭제")
                            // 메세지
                            builder.setMessage("메모를 삭제하시겠습니까?")

                            builder.setNegativeButton("취소", null)

                            // 다이얼로그 삭제 클릭 시
                            builder.setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                DAO.deleteData(mainActivity, MainFragment.rowIdx)
                                mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                            }

                            // 다이얼로그 보여주기
                            builder.show()
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
                    mainActivity.removeFragment(MainActivity.RESULT_FRAGMENT)
                }
            }

            // 메모 정보 불러오기
            val memo = DAO.selectData(mainActivity, MainFragment.rowIdx)
            val(idx, title, content, date) = memo

            // 메모 정보 화면에 표시
            textViewResultTitle.text = title
            textViewResultContent.text = content
            textViewResultDate.text = date
        }

        return fragmentResultBinding.root
    }
}