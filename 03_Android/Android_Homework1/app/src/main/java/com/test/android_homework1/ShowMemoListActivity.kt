package com.test.android_homework1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_homework1.databinding.ActivityShowMemoListBinding
import com.test.android_homework1.databinding.RowBinding

class ShowMemoListActivity : AppCompatActivity() {

    lateinit var activityShowMemoListBinding: ActivityShowMemoListBinding
    lateinit var memoActivityResultLauncher: ActivityResultLauncher<Intent>

    lateinit var category : String

    // 메모 객체를 저장하기 위한 리스트
    var memoList = ArrayList<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowMemoListBinding = ActivityShowMemoListBinding.inflate(layoutInflater)
        setContentView(activityShowMemoListBinding.root)

        // 액티비티 이동 시 실행될 코드
        var m1 = ActivityResultContracts.StartActivityForResult()
        memoActivityResultLauncher = registerForActivityResult(m1){
            val title = it.data?.getStringExtra("newTitle")
            val content = it.data?.getStringExtra("newContent")

            // 메모 추가
            if (it.resultCode == RESULT_OK){
                val memo = Memo()
                memo.title = title!!
                memo.content = content!!
                memoList.add(memo)
            }

            // 메모 수정
            else if (it.resultCode == (RESULT_FIRST_USER + 1)){
                // 수정할 때만 넘겨줬던 메모의 인덱스를 받는다.
                val memoPosition = it.data?.getIntExtra("memoPosition", 0)
                // 변경 전 제목을 받는다.
                var beforeTitle = it.data?.getStringExtra("beforeTitle")

                // 제목이 빈칸이 아니면 수정
                if (title != ""){
                    // 메모 제목 리스트 변경
                    memoList[memoPosition!!].title = title!!

                    // 변경된 제목으로 내용도 수정할 수 있도록 beforeTitle 저장 값 변경
                    beforeTitle = memoList[memoPosition!!].title
                }

                // 내용이 빈칸이 아니면 수정
                if (content != ""){
                    memoList[memoPosition!!].content = content!!
                }
            }

            // 리사이클러뷰 업데이트
            val adapter = activityShowMemoListBinding.recyclerViewMemo.adapter as RecyclerAdapterClass
            adapter.notifyDataSetChanged()
        }

        activityShowMemoListBinding.run {
            // 카테고리 이름을 받는다.
            category = intent.getStringExtra("category")!!
            // 메인에 저장되어 있던 메모 리스트를 받는다.
            val list = intent.getParcelableArrayListExtra<Memo>("memo")

            // 리스트가 null이 아니면 그대로 적용
            if (list != null){
                memoList = list
            }

            recyclerViewMemo.run {
                textViewMemoList.text = "${category} 카테고리 메모 목록"

                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@ShowMemoListActivity)
            }
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
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

    // 뒤로가기 버튼 클릭 시 메인으로 이동
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val memoIntent = Intent(this@ShowMemoListActivity, MainActivity::class.java)
            // 카테고리 이름 추가
            memoIntent.putExtra("category", category)
            // 메모 리스트 추가
            memoIntent.putExtra("memo", memoList)

            setResult(RESULT_FIRST_USER+2, memoIntent)
            // 액티비티 종료
            finish()
        }
    }

    // 리사이클러뷰 어뎁터
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewMemoTitle : TextView

            init {
                textViewMemoTitle = rowBinding.textViewRowName

                rowBinding.root.run {
                    setOnCreateContextMenuListener { menu, v, menuInfo ->
                        menu.setHeaderTitle("${memoList[adapterPosition].title}")
                        menuInflater.inflate(R.menu.context_memo_menu, menu)

                        // 메모 수정
                        menu[0].setOnMenuItemClickListener {
                            val updateMemoIntent = Intent(this@ShowMemoListActivity, UpdateMemoActivity::class.java)
                            updateMemoIntent.putExtra("memoTitle", memoList[adapterPosition].title)
                            updateMemoIntent.putExtra("memoContent", memoList[adapterPosition].content)
                            updateMemoIntent.putExtra("memoPosition", adapterPosition)

                            memoActivityResultLauncher.launch(updateMemoIntent)

                            false
                        }

                        // 메모 삭제
                        menu[1].setOnMenuItemClickListener {
                            memoList.removeAt(adapterPosition)

                            this@RecyclerAdapterClass.notifyDataSetChanged()

                            false
                        }
                    }

                    // 메모 클릭
                    setOnClickListener {
                        val showMemoIntent = Intent(this@ShowMemoListActivity, ShowMemoContentActivity::class.java)
                        showMemoIntent.putExtra("title", memoList[adapterPosition].title)
                        showMemoIntent.putExtra("content", memoList[adapterPosition].content)
                        startActivity(showMemoIntent)
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
            return memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewMemoTitle.text = memoList[position].title
        }
    }
}

// memo 객체 직렬화를 위한 class
class Memo() : Parcelable {
    lateinit var title : String
    lateinit var content : String

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()!!
        content = parcel.readString()!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
    }

    companion object CREATOR : Parcelable.Creator<Memo> {
        override fun createFromParcel(parcel: Parcel): Memo {
            return Memo(parcel)
        }

        override fun newArray(size: Int): Array<Memo?> {
            return arrayOfNulls(size)
        }
    }
}