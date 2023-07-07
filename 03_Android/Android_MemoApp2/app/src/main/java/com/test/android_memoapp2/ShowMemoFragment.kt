package com.test.android_memoapp2

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
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_memoapp2.databinding.FragmentShowMemoBinding
import com.test.android_memoapp2.databinding.RowBinding

class ShowMemoFragment : Fragment() {

    lateinit var fragmentShowMemoBinding: FragmentShowMemoBinding
    lateinit var mainActivity: MainActivity

    // 메모 정보를 담을 리스트
    companion object{
        // 사용자가 누른 메모 번호
        var rowMemoPosition = 0
        // 사용자가 누른 메모의 idx
        var rowMemoIdx = 0
        // 메모 리스트
        var memoList = mutableListOf<MemoClass>()
        // 해당 메모의 카테고리
        var category = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentShowMemoBinding = FragmentShowMemoBinding.inflate(inflater)

        fragmentShowMemoBinding.run {

            // 해당 메모의 카테고리
            category = ShowCategoryFragment.categoryList[ShowCategoryFragment.rowCategoryPosition]

            toolbarShowMemo.run {
                title = "$category"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.show_add_menu)

                // 추가 버튼 클릭 시 프래그먼트 전환
                setOnMenuItemClickListener {
                    mainActivity.replaceFragment(MainActivity.ADD_MEMO_FRAGMENT, true, true)

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
                    mainActivity.removeFragment(MainActivity.SHOW_MEMO_FRAGMENT)
                }
            }

            recyclerViewShowMemo.run {
                adapter = RecyclerViewMemoAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentShowMemoBinding.root
    }

    // 리사이클러뷰 어뎁터 클래스
    inner class RecyclerViewMemoAdapter : RecyclerView.Adapter<RecyclerViewMemoAdapter.MainViewHolderClass>(){

        inner class MainViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRow : TextView

            init {
                textViewRow = rowBinding.textViewRow

                rowBinding.root.setOnClickListener {
                    rowMemoPosition = adapterPosition
                    rowMemoIdx = memoList[adapterPosition].idx
                    mainActivity.replaceFragment(MainActivity.RESULT_MEMO_FRAMGENT, true, true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val categoryRowBinding = RowBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(categoryRowBinding)

            categoryRowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewRow.text = memoList[position].title
        }
    }

    override fun onResume() {
        super.onResume()

        // 데이터를 불러온다.
        memoList = DAO.selectAllMemo(mainActivity, category)

        // 리사이클러뷰 갱신
        fragmentShowMemoBinding.recyclerViewShowMemo.adapter?.notifyDataSetChanged()
    }
}

data class MemoClass(var idx:Int, var title:String, var content:String, var date:String)