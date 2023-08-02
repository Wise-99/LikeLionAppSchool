package com.test.mini02_boardproject01

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.mini02_boardproject01.databinding.FragmentPostListBinding
import com.test.mini02_boardproject01.databinding.RowPostListBinding
import com.test.mini02_boardproject01.repository.UserRepository
import com.test.mini02_boardproject01.vm.ViewModelBoard

class PostListFragment : Fragment() {

    lateinit var fragmentPostListBinding: FragmentPostListBinding
    lateinit var mainActivity: MainActivity
    lateinit var boardMainFragment: BoardMainFragment

    lateinit var viewModelBoard : ViewModelBoard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostListBinding = FragmentPostListBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        viewModelBoard = ViewModelProvider(this)[ViewModelBoard::class.java]

        fragmentPostListBinding.run {

            recyclerViewPostAll.run {
                adapter = AllRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

            recyclerViewPostListResult.run {
                adapter = ResultRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }

            searchBarPostList.run{
                hint = "검색어를 입력해주세요"
                inflateMenu(R.menu.menu_post_list)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.item_post_list_add -> {
                            mainActivity.replaceFragment(MainActivity.POST_WRITE_FRAGMENT, true, null)
                        }
                    }
                    true
                }
            }

            searchViewPostList.run{
                hint = "검색어를 입력해주세요"
            }

            viewModelBoard.run {
                postList.observe(mainActivity) {
                    recyclerViewPostAll.adapter?.notifyDataSetChanged()
                }
            }

        }

        return fragmentPostListBinding.root
    }

    override fun onResume() {
        super.onResume()
        // ViewModel 에 있는 모든 데이터를 가져오는 메서드를 호출한다.
        viewModelBoard.getAll()
    }

    // 모든 게시글 목록을 보여주는 리사이클러 뷰의 어뎁터
    inner class AllRecyclerViewAdapter : RecyclerView.Adapter<AllRecyclerViewAdapter.AllViewHolder>(){
        inner class AllViewHolder(rowPostListBinding: RowPostListBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){
            val rowPostListSubject : TextView
            val rowPostListNickName:TextView

            init {
                rowPostListSubject = rowPostListBinding.rowPostListSubject
                rowPostListNickName = rowPostListBinding.rowPostListNickName
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
            val rowPostListBinding = RowPostListBinding.inflate(layoutInflater)
            val allViewHolder = AllViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowPostListBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.POST_READ_FRAGMENT, true, null)
            }

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return viewModelBoard.postList.value?.size!!
        }

        override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
            holder.rowPostListSubject.text = viewModelBoard.postList.value?.get(position)?.postSubject

            val idx = (viewModelBoard.postList.value?.get(position)?.postWriterIdx) as Double
            holder.rowPostListNickName.text = UserRepository.idxToNickname(idx)
        }
    }

    inner class ResultRecyclerViewAdapter : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){
        inner class ResultViewHolder(rowPostListBinding: RowPostListBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){
            val rowPostListSubject : TextView
            val rowPostListNickName:TextView

            init {
                rowPostListSubject = rowPostListBinding.rowPostListSubject
                rowPostListNickName = rowPostListBinding.rowPostListNickName
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
            val rowPostListBinding = RowPostListBinding.inflate(layoutInflater)
            val allViewHolder = ResultViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowPostListBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainActivity.POST_READ_FRAGMENT, true, null)
            }

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return 100
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.rowPostListSubject.text = "제목입니다 : $position"
            holder.rowPostListNickName.text = "작성자 : $position"
        }
    }
}