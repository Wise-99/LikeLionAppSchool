package com.test.android_homework1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_homework1.databinding.ActivityMainBinding
import com.test.android_homework1.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var categoryActivityResultLauncher: ActivityResultLauncher<Intent>

    // 카테고리와 메모를 저장할 Map
    val totalList = HashMap<String, ArrayList<Memo>?>()

    // 카테고리 이름을 저장할 list
    val categoryList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 다른 액티비티에서 메인으로 왔을 때 실행되는 코드
        var c1 = ActivityResultContracts.StartActivityForResult()
        categoryActivityResultLauncher = registerForActivityResult(c1){

            val categoryName = it.data?.getStringExtra("newCategoryName")
            val beforeCategoryName = it.data?.getStringExtra("beforeCategoryName")

            // 카테고리 추가
            if (it.resultCode == RESULT_OK){
                totalList[categoryName!!] = null // 카테고리 추가 시 메모는 없기에 null로 설정
                categoryList.add(categoryName!!)
            }

            // 카테고리 이름 수정
            else if (it.resultCode == RESULT_FIRST_USER){
                // 수정 시에만 넘겨줬던 카테고리의 인덱스 번호를 받는다.
                val categoryPosition = it.data?.getIntExtra("categoryPosition", 0)

                // 메모리스트를 복사하여 카테고리 새로 생성
                val memoList = totalList[beforeCategoryName]
                totalList[categoryName!!] = memoList

                // 메모 포함 이전 이름을 가진 카테고리 삭제
                totalList.remove(beforeCategoryName)

                // 카테고리 이름 리스트 변경
                categoryList[categoryPosition!!] = categoryName!!
            }

            // 메모 저장
            else if (it.resultCode == RESULT_FIRST_USER + 2){
                // 메모 리스트 화면에 전달했던 카테고리 이름을 받는다.
                val category = it.data?.getStringExtra("category")
                // 리스트 화면에 있던 메모 리스트를 받는다.
                val list = it.data?.getParcelableArrayListExtra<Memo>("memo")
                // 리스트가 null이 아니면 카테고리 이름과 연결하여 저장
                if(list != null){
                    totalList[category!!] = list as ArrayList<Memo>
                }
            }

            // 리사이클러뷰 업데이트(카테고리 추가, 수정 모두)
            val adapter = activityMainBinding.recyclerViewCategory.adapter as RecyclerAdapterClass
            adapter.notifyDataSetChanged()
        }

        activityMainBinding.run {
            // 리사이클러뷰 설정
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
                textViewRowCategory = rowBinding.textViewRowName

                // 리사이클러뷰 컨텍스트 메뉴
                rowBinding.root.run {
                    setOnCreateContextMenuListener { menu, v, menuInfo ->
                        menu.setHeaderTitle("${categoryList[adapterPosition]}")
                        menuInflater.inflate(R.menu.context_category_menu, menu)

                        // 카테고리 수정
                        menu[0].setOnMenuItemClickListener {
                            val updateIntent = Intent(this@MainActivity, UpdateCategoryActivity::class.java)
                            // 수정 전 이름 전달
                            updateIntent.putExtra("categoryName", categoryList[adapterPosition])
                            // 수정할 카테고리 이름의 인덱스 전달
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
                        // 카테고리 이름 전달
                        memoIntent.putExtra("category", categoryList[adapterPosition])

                        // 메모가 있다면 메모 리스트를 넘겨줌
                        if (totalList[categoryList[adapterPosition]] != null){
                            memoIntent.putExtra("memo", totalList[categoryList[adapterPosition]])
                        }

                        categoryActivityResultLauncher.launch(memoIntent)
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