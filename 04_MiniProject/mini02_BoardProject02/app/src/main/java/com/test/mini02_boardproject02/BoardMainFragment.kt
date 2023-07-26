package com.test.mini02_boardproject02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.mini02_boardproject02.databinding.FragmentBoardMainBinding
import com.test.mini02_boardproject02.databinding.HeaderBoardMainBinding

class BoardMainFragment : Fragment() {

    lateinit var fragmentBoardMainBinding: FragmentBoardMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBoardMainBinding = FragmentBoardMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentBoardMainBinding.run {
            // toolbar
            toolbarBoardMain.run {
                title = "게시판 메인"

                setNavigationIcon(R.drawable.menu_24px)
                setNavigationOnClickListener {
                    // 네비게이션 뷰를 보여준다.
                    drawerLayoutBoardMain.open()
                }
            }

            // drawerView
            navigationViewBoardMain.run {
                // header
                val headerBoardMainBinding = HeaderBoardMainBinding.inflate(inflater)
                headerBoardMainBinding.textViewHeaderBoardMainNickName.text = "홍길동님"
                addHeaderView(headerBoardMainBinding.root)

                // 항목 선택 시 동작하는 리스너
                setNavigationItemSelectedListener {
                    // 누른 메뉴를 체크 상태로 둔다.
                    // it.isChecked = true

                    // 사용자가 누른 메뉴의 id로 분기한다.
                    when(it.itemId){
                        // 전체 게시판
                        R.id.item_board_main_all -> {
                            textViewTest.text = "전체 게시판을 눌렀습니다"
                        }
                        // 자유 게시판
                        R.id.item_board_main_free -> {
                            textViewTest.text = "자유 게시판을 눌렀습니다"
                        }
                        // 유머 게시판
                        R.id.item_board_main_gag -> {

                        }
                        // 질문 게시판
                        R.id.item_board_main_qna -> {

                        }
                        // 스포츠 게시판
                        R.id.item_board_main_sports -> {

                        }
                        // 사용자 정보 수정
                        R.id.item_board_main_user_info -> {

                        }
                        // 로그아웃
                        R.id.item_board_main_logout -> {

                        }
                        // 회원탈퇴
                        R.id.item_board_main_sign_out -> {

                        }
                    }

                    // 닫아준다.
                    drawerLayoutBoardMain.close()

                    true
                }
            }
        }

        return fragmentBoardMainBinding.root
    }
}