package com.test.android_memoapp01

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_memoapp01.databinding.FragmentMainBinding
import com.test.android_memoapp01.databinding.RowMainBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    // 메모 정보를 담을 리스트
    companion object{
        // 사용자가 누른 항목 번호
        var rowPosition = 0
        // 사용자가 누른 항목의 idx
        var rowIdx = 0
        // 메모 리스트
        var memoList = mutableListOf<MemoClass>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)

        fragmentMainBinding.run {
            toolbarMain.run {
                title = "메모 앱"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.main_menu)

                // 메모 추가 버튼 클릭 시
                setOnMenuItemClickListener {
                    mainActivity.replaceFragment(MainActivity.ADD_FRAGMENT, true, true)
                    false
                }
            }

            recyclerViewMain.run {
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentMainBinding.root
    }

    // 리사이클러뷰 어뎁터 클래스
    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolderClass>(){

        inner class MainViewHolderClass(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            var textViewMainRowDate : TextView
            var textViewMainRowTitle : TextView

            init {
                textViewMainRowDate = rowMainBinding.textViewRowDate
                textViewMainRowTitle = rowMainBinding.textViewRowTitle

                rowMainBinding.root.setOnClickListener {
                    rowPosition = adapterPosition
                    rowIdx = memoList[adapterPosition].idx
                    mainActivity.replaceFragment(MainActivity.RESULT_FRAGMENT, true, true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(rowMainBinding)

            rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewMainRowDate.text = memoList[position].date
            holder.textViewMainRowTitle.text = memoList[position].title
        }
    }

    override fun onResume() {
        super.onResume()

        // 데이터를 불러온다.
        memoList = DAO.selectAllData(mainActivity)

        // 리사이클러뷰 갱신
        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }
}