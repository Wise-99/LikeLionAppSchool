package com.test.android36_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.test.android36_recyclerview.databinding.ActivityMainBinding
import com.test.android36_recyclerview.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    val imgRes = intArrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8
    )

    val data1 = arrayOf(
        "토고", "프랑스 문자열을 길게 작성해주세요 문자열을 길게 작성해주세요 문자열을 길게 작성해주세요",
        "스위스", "스페인",
        "일본 문자열을 길게 작성해주세요 문자열을 길게 작성해주세요 문자열을 길게 작성해주세요",
        "독일", "브라질", "대한민국"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            recyclerView.run{
                adapter = RecyclerAdapterClass()
                // RecyclerView의 항목을 어떻게 보여줄 것인가를 설정
                // 위에서 아래 방향으로
                layoutManager = LinearLayoutManager(this@MainActivity)

                // 그리드
                // 한 줄에 몇 칸을 사용할 것인지....
                // 한줄에 두 칸 사용
                // 1로 설정 시 LinearLayoutManager와 동일
                // layoutManager = GridLayoutManager(this@MainActivity, 2)

                // 항목 View의 크기가 다를 경우 GridLayoutManager는 같은 행의 모든 뷰가 같은 크기로 고정되지만
                // StaggeredGridLayoutManager는 항목 View의 크기는 필요한 만큼만 사용하고
                // 화면에 빈칸이 없도록 배치를 조정해준다.
                // 세로 방향
                // layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                // 가로 방향
                // layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            }
        }
    }

    // RecyclerView의 AdapterClass
    // 1. 아무 것도 상속받지 않은 클래스를 만들어준다.
    // 2. ViewHolder를 만들어준다.
    // 3. AdapterClass를 RecyclerView.Adapter를 상속받게 한다.
    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>(){

        // RecyclerView의 Row 하나가 가지고 있는 View들의 객체의 Id 값을 가지고 있는 Holder Class
        // 주 생성자로 ViewBinding 객체를 받는다.
        // 부모의 생성자에게 행 하나로 사용할 View를 전달한다.
        inner class ViewHolderClass(rowBinding: RowBinding) : ViewHolder(rowBinding.root), OnClickListener{
            var textViewRow:TextView
            var imageViewRow:ImageView

            init {
                textViewRow = rowBinding.textViewRow
                imageViewRow = rowBinding.imageViewRow
            }

            override fun onClick(v: View?) {
                // ViewHolder를 통해 항목의 순서 값을 가지고 온다.
                activityMainBinding.textView.text = data1[adapterPosition]
            }
        }

        // ViewHolder의 객체를 생성해서 반환한다.
        // 전체 행의 개수가 아닌 필요한 만큼만 행으로 사용할 View를 만들고 ViewHolder도 생성한다.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            // ViewBinding
            val rowBinding = RowBinding.inflate(layoutInflater)
            // ViewHolder
            val viewHolderClass = ViewHolderClass(rowBinding)

            // 클릭 이벤트를 설정해준다.
            rowBinding.root.setOnClickListener(viewHolderClass)

            // 항목 View의 가로세로 길이를 설정해준다(터치 때문에)
            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        // 전체 행의 개수를 반환한다.
        override fun getItemCount(): Int {
            return imgRes.size
        }

        // viewHolder를 통해 View에 접근하여 View에 값을 설정한다.
        // 첫번째 : ViewHolder 객체
        // 두번째 : 특정 행의 순서 값
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRow.text = data1[position]
            holder.imageViewRow.setImageResource(imgRes[position])
        }
    }
}