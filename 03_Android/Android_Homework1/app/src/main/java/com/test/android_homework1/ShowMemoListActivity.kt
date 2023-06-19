package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_homework1.databinding.ActivityShowMemoListBinding
import com.test.android_homework1.databinding.MemoRowBinding

class ShowMemoListActivity : AppCompatActivity() {

    lateinit var activityShowMemoListBinding: ActivityShowMemoListBinding
    lateinit var memoActivityResultLauncher: ActivityResultLauncher<Intent>
    val memoTitleList = mutableListOf<String>()
    val memoMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowMemoListBinding = ActivityShowMemoListBinding.inflate(layoutInflater)
        setContentView(activityShowMemoListBinding.root)

        var m1 = ActivityResultContracts.StartActivityForResult()
        memoActivityResultLauncher = registerForActivityResult(m1){
            val title = it.data?.getStringExtra("newTitle")
            val content = it.data?.getStringExtra("newContent")

            // 메모 추가
            if (it.resultCode == RESULT_OK){
                memoTitleList.add(title!!)
                memoMap[title!!] = content!!
            }

            // 메모 수정
            else if (it.resultCode == (RESULT_FIRST_USER + 1)){
                // 수정할 때만 넘겨줬던 메모의 인덱스를 받는다.
                val memoPosition = it.data?.getIntExtra("memoPosition", 0)

                val beforeContent = it.data?.getStringExtra("beforeContent")
                var beforeTitle = it.data?.getStringExtra("beforeTitle")

                // 제목이 null이 아니면 수정
                if (title != ""){
                    // 메모 제목 리스트 변경
                    memoTitleList[memoPosition!!] = title!!

                    // 내용을 그대로 넣기 위해 복사
                    val copy = memoMap[beforeTitle]

                    // 이전에 저장했던 메모는 삭제
                    memoMap.remove(beforeTitle)

                    // 이전에 있던 내용을 새로운 제목으로 저장
                    memoMap[title] = copy!!

                    // 메모 제목 리스트 변경
                    memoTitleList[memoPosition] = title

                    // 변경된 제목으로 내용도 수정할 수 있도록 beforeTitle 저장 값 변경
                    beforeTitle = memoTitleList[memoPosition]
                }

                // 내용이 null이 아니면 수정
                if (content != ""){
                    memoMap[beforeTitle!!] = content!!
                    Log.d("qwerty", "${memoMap.keys} + ${memoMap[beforeTitle!!]}")
                }
            }

            // 리사이클러뷰 업데이트
            val adapter = activityShowMemoListBinding.recyclerViewMemo.adapter as RecyclerAdapterClass
            adapter.notifyDataSetChanged()
        }

        activityShowMemoListBinding.run {
            recyclerViewMemo.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@ShowMemoListActivity)
            }
        }
    }

    // 메모 추가 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.memo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 메모 추가 선택 시 Activity 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMemo -> {
                val addIntent = Intent(this@ShowMemoListActivity, AddMemoActivity::class.java)
                memoActivityResultLauncher.launch(addIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 리사이클러뷰 어뎁터
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(memoRowBinding: MemoRowBinding) : RecyclerView.ViewHolder(memoRowBinding.root){
            var textViewMemoTitle : TextView

            init {
                textViewMemoTitle = memoRowBinding.TextViewMemoRowTitle

                memoRowBinding.root.run {
                    setOnCreateContextMenuListener { menu, v, menuInfo ->
                        menu.setHeaderTitle("${memoTitleList[adapterPosition]}")
                        menuInflater.inflate(R.menu.context_memo_menu, menu)

                        // 메모 수정
                        menu[0].setOnMenuItemClickListener {
                            val updateMemoIntent = Intent(this@ShowMemoListActivity, UpdateMemoActivity::class.java)
                            updateMemoIntent.putExtra("memoTitle", memoTitleList[adapterPosition])
                            updateMemoIntent.putExtra("memoContent", memoMap[memoTitleList[adapterPosition]])
                            updateMemoIntent.putExtra("memoPosition", adapterPosition)

                            memoActivityResultLauncher.launch(updateMemoIntent)

                            false
                        }

                        // 메모 삭제
                        menu[1].setOnMenuItemClickListener {
                            memoMap.remove(memoTitleList[adapterPosition])
                            memoTitleList.removeAt(adapterPosition)

                            this@RecyclerAdapterClass.notifyDataSetChanged()

                            false
                        }
                    }

                    setOnClickListener {
                        val showMemoIntent = Intent(this@ShowMemoListActivity, ShowMemoContentActivity::class.java)
                        showMemoIntent.putExtra("title", memoTitleList[adapterPosition])
                        showMemoIntent.putExtra("content", memoMap[memoTitleList[adapterPosition]])
                        startActivity(showMemoIntent)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val memoRowBinding = MemoRowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(memoRowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            memoRowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return memoTitleList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewMemoTitle.text = memoTitleList[position]
        }
    }
}
