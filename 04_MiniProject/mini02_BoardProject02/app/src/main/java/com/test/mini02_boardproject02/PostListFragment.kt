package com.test.mini02_boardproject02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.test.mini02_boardproject02.databinding.FragmentPostListBinding
import com.test.mini02_boardproject02.databinding.RowPostListBinding
import com.test.mini02_boardproject02.vm.PostViewModel

class PostListFragment : Fragment() {

    lateinit var fragmentPostListBinding: FragmentPostListBinding
    lateinit var mainActivity: MainActivity

    lateinit var postViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostListBinding = FragmentPostListBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        postViewModel = ViewModelProvider(mainActivity)[PostViewModel::class.java]
        postViewModel.run {
            postDataList.observe(mainActivity){
                fragmentPostListBinding.recyclerViewPostAll.adapter?.notifyDataSetChanged()
            }
        }

        fragmentPostListBinding.run {

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

                addTransitionListener { searchView, previousState, newState ->
                    // searchBar를 눌러 searchView가 보일 때
                    if(newState == SearchView.TransitionState.SHOWING){
                        // Snackbar.make(fragmentPostListBinding.root, "Showing", Snackbar.LENGTH_SHORT).show()
                        postViewModel.resetPostList()
                    }
                    // searchView의 back 버튼을 눌러 searchView가 사라지고 searchBar가 보일 때
                    else if(newState == SearchView.TransitionState.HIDING){
                        // Snackbar.make(fragmentPostListBinding.root, "Hiding", Snackbar.LENGTH_SHORT).show()
                        postViewModel.getPostAll(arguments?.getLong("postType")!!)
                    }
                }

                editText.setOnEditorActionListener { v, actionId, event ->
                    Snackbar.make(fragmentPostListBinding.root, text!!, Snackbar.LENGTH_SHORT).show()
                    true
                }
            }

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

            // 게시판 타입 번호를 전달하여 게시글 정보를 가져온다.
            postViewModel.getPostAll(arguments?.getLong("postType")!!)
        }

        return fragmentPostListBinding.root
    }

    // 모든 게시글 목록을 보여주는 리사이클러 뷰의 어뎁터
    inner class AllRecyclerViewAdapter : RecyclerView.Adapter<AllRecyclerViewAdapter.AllViewHolder>(){
        inner class AllViewHolder(rowPostListBinding: RowPostListBinding) : RecyclerView.ViewHolder(rowPostListBinding.root){
            val rowPostListSubject : TextView
            val rowPostListNickName:TextView

            init {
                rowPostListSubject = rowPostListBinding.rowPostListSubject
                rowPostListNickName = rowPostListBinding.rowPostListNickName

                rowPostListBinding.root.setOnClickListener {
                    // 항목번째 객체에서 글 번호를 가져온다.
                    val readPostIdx = postViewModel.postDataList.value?.get(adapterPosition)?.postIdx
                    val newBundle = Bundle()
                    newBundle.putLong("readPostIdx", readPostIdx!!)
                    mainActivity.replaceFragment(MainActivity.POST_READ_FRAGMENT, true, newBundle)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
            val rowPostListBinding = RowPostListBinding.inflate(layoutInflater)
            val allViewHolder = AllViewHolder(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return allViewHolder
        }

        override fun getItemCount(): Int {
            return postViewModel.postDataList.value?.size!!
        }

        override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
            holder.rowPostListSubject.text = postViewModel.postDataList.value?.get(position)?.postSubject
            // holder.rowPostListNickName.text = postViewModel.postWriterNicknameList.value?.get(position)
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
            return postViewModel.postDataList.value?.size!!
        }

        override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
            holder.rowPostListSubject.text = postViewModel.postDataList.value?.get(position)?.postSubject
            holder.rowPostListNickName.text = postViewModel.postWriterNicknameList.value?.get(position)
        }
    }
}