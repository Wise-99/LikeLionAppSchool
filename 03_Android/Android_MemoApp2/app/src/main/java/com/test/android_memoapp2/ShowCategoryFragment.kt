package com.test.android_memoapp2

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_memoapp2.databinding.CategoryDialogBinding
import com.test.android_memoapp2.databinding.FragmentShowCategoryBinding
import com.test.android_memoapp2.databinding.RowBinding
import kotlin.concurrent.thread

class ShowCategoryFragment : Fragment() {

    lateinit var fragmentShowCategoryBinding: FragmentShowCategoryBinding
    lateinit var mainActivity: MainActivity
    lateinit var imm : InputMethodManager

    // 카테고리 정보를 담을 리스트
    companion object{
        // 사용자가 누른 항목 번호
        var rowCategoryPosition = 0
        // 메모 리스트
        var categoryList = mutableListOf<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShowCategoryBinding = FragmentShowCategoryBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fragmentShowCategoryBinding.run {
            toolbarShowCategory.run {
                title = "카테고리 목록"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.show_add_menu)

                // 추가 버튼 클릭 시
                setOnMenuItemClickListener {
                    val dialogBinding = CategoryDialogBinding.inflate(layoutInflater)
                    val builder = AlertDialog.Builder(mainActivity)
                    builder.setTitle("카테고리 등록")

                    builder.setView(dialogBinding.root)

                    // 포커스 설정
                    dialogBinding.editTextDialogCategory.requestFocus()
                    // 키보드 올림
                    thread {
                        SystemClock.sleep(1000)
                        imm.showSoftInput(dialogBinding.editTextDialogCategory, 0)
                    }

                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        // 입력한 내용을 가져온다.
                        val name = dialogBinding.editTextDialogCategory.text.toString()
                        DAO.createCategory(mainActivity, name)

                        // 데이터를 불러온다.
                        categoryList = DAO.selectAllTable(mainActivity)
                        recyclerViewShowCategory.adapter?.notifyDataSetChanged()
                    }

                    builder.setNegativeButton("취소", null)

                    // 다이얼로그를 띄운다.
                    builder.show()

                    false
                }
            }

            recyclerViewShowCategory.run {
                adapter = RecyclerViewCategoryAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }

        return fragmentShowCategoryBinding.root
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            0 -> {
                // 카테고리 수정
                val dialogBinding = CategoryDialogBinding.inflate(layoutInflater)
                val builder = AlertDialog.Builder(mainActivity)
                builder.setTitle("카테고리 수정")

                builder.setView(dialogBinding.root)
                imm = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                // 포커스 설정
                dialogBinding.editTextDialogCategory.requestFocus()
                // 키보드 올림
                thread {
                    SystemClock.sleep(1000)
                    imm.showSoftInput(dialogBinding.editTextDialogCategory, 0)
                }

                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    // 입력한 내용을 가져온다.
                    val name = dialogBinding.editTextDialogCategory.text.toString()
                    DAO.updateCategory(mainActivity, categoryList[item.groupId], name)
                    // 데이터를 불러온다.
                    categoryList = DAO.selectAllTable(mainActivity)
                    // 리사이클러 뷰 새로고침
                    fragmentShowCategoryBinding.recyclerViewShowCategory.adapter?.notifyDataSetChanged()
                }

                builder.setNegativeButton("취소", null)

                // 다이얼로그를 띄운다.
                builder.show()
            }
            1 -> {
                // 카테고리 삭제
                DAO.deleteCategory(mainActivity, categoryList[item.groupId])
                // 데이터를 불러온다.
                categoryList = DAO.selectAllTable(mainActivity)
                // 리사이클러 뷰 새로고침
                fragmentShowCategoryBinding.recyclerViewShowCategory.adapter?.notifyDataSetChanged()
            }
        }



        return true
    }


    // 리사이클러뷰 어뎁터 클래스
    inner class RecyclerViewCategoryAdapter : RecyclerView.Adapter<RecyclerViewCategoryAdapter.MainViewHolderClass>(){

        inner class MainViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRow : TextView

            init {
                textViewRow = rowBinding.textViewRow

                rowBinding.root.run {
                    setOnClickListener {
                        rowCategoryPosition = adapterPosition
                        mainActivity.replaceFragment(MainActivity.SHOW_MEMO_FRAGMENT, true, true)
                    }

                    setOnCreateContextMenuListener { menu, v, menuInfo ->
                        menu.setHeaderTitle("${categoryList[adapterPosition]}")
                        menu.add(adapterPosition, 0,0,"수정")
                        menu.add(adapterPosition, 1,0,"삭제")
                    }

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
            return categoryList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewRow.text = categoryList[position]
        }
    }

    override fun onResume() {
        super.onResume()

        // 데이터를 불러온다.
        categoryList = DAO.selectAllTable(mainActivity)

        // 리사이클러뷰 갱신
        fragmentShowCategoryBinding.recyclerViewShowCategory.adapter?.notifyDataSetChanged()
    }
}