package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_homework1.databinding.ActivityMainBinding
import com.test.android_homework1.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var categoryActivityResultLauncher: ActivityResultLauncher<Intent>

    // 카테고리와 메모를 저장할 Map
    val totalList = HashMap<String, HashMap<String, String>?>()

    // 카테고리 이름을 저장할 list
    val categoryList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        var c1 = ActivityResultContracts.StartActivityForResult()
        categoryActivityResultLauncher = registerForActivityResult(c1){

            val categoryName = it.data?.getStringExtra("newCategoryName")
            val beforeCategoryName = it.data?.getStringExtra("beforeCategoryName")

            // 카테고리 추가
            if (it.resultCode == RESULT_OK){
                totalList[categoryName!!] = null
                categoryList.add(categoryName!!)
            }

            // 카테고리 수정
            else if (it.resultCode == RESULT_FIRST_USER){
                // 수정 시에만 넘겨줬던 카테고리의 인덱스 번호를 받는다.
                val categoryPosition = it.data?.getIntExtra("categoryPosition", 0)

                // 카테고리에 메모가 존재하면 복사하여 메모 포함 카테고리 새로 생성
                if (totalList[beforeCategoryName] != null){
                    val memoList = totalList[beforeCategoryName]
                    totalList[categoryName!!] = memoList!!
                }

                // 메모 포함 카테고리 삭제
                totalList.remove(beforeCategoryName)

                // 카테고리 이름 변경
                categoryList[categoryPosition!!] = categoryName!!
            }

            // 리사이클러뷰 업데이트
            val adapter = activityMainBinding.recyclerViewCategory.adapter as RecyclerAdapterClass
            adapter.notifyDataSetChanged()
        }

        activityMainBinding.run {
            recyclerViewCategory.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    // 카테고리 추가 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 카테고리 추가 선택 시 Activity 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addCategory -> {
                val addIntent = Intent(this@MainActivity, AddCategoryActivity::class.java)
                categoryActivityResultLauncher.launch(addIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 리사이클러뷰 어뎁터 설정
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            val textViewRowCategory : TextView

            init {
                textViewRowCategory = rowBinding.textViewRowCategoryName

                // 리사이클러뷰 컨텍스트 메뉴
                rowBinding.root.run {
                    setOnCreateContextMenuListener { menu, v, menuInfo ->
                        menu.setHeaderTitle("${categoryList[adapterPosition]}")
                        menuInflater.inflate(R.menu.context_category_menu, menu)

                        // 카테고리 수정
                        menu[0].setOnMenuItemClickListener {
                            val updateIntent = Intent(this@MainActivity, UpdateCategoryActivity::class.java)
                            updateIntent.putExtra("categoryName", categoryList[adapterPosition])
                            updateIntent.putExtra("categoryPosition", adapterPosition)
                            categoryActivityResultLauncher.launch(updateIntent)

                            false
                        }

                        // 카테고리 삭제
                        menu[1].setOnMenuItemClickListener {
                            totalList.remove(categoryList[adapterPosition])
                            categoryList.removeAt(adapterPosition)

                            this@RecyclerAdapterClass.notifyDataSetChanged()

                            false
                        }
                    }

                    // 카테고리 클릭
                    setOnClickListener {
                        val memoIntent = Intent(this@MainActivity, ShowMemoListActivity::class.java)
                        memoIntent.putExtra("memo", totalList[categoryList[adapterPosition]])
                        startActivity(memoIntent)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
           return categoryList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowCategory.text = categoryList[position]
        }
    }
}