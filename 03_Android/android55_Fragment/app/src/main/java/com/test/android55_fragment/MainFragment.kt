package com.test.android55_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android55_fragment.databinding.FragmentMainBinding
import com.test.android55_fragment.databinding.RowBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    // Fragment가 보여줄 화면을 생성하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment를 관리하는 Activity 객체를 가져온다.
        mainActivity = activity as MainActivity
        // ViewBinding
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)

        fragmentMainBinding.run {
            buttonMain1.setOnClickListener {
                // Fragment를 교체한다.
                mainActivity.replaceFragment(FragmentName.FRAGMENT_INPUT, true, true)
            }

            recyclerViewMain.run {
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentMainBinding.root
    }

    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHilderClass>(){
        inner class MainViewHilderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRow : TextView

            init{
                textViewRow = rowBinding.textViewRow

                rowBinding.root.setOnClickListener {
                    // 터치한 항목의 위치 값을 변수에 담아준다.
                    mainActivity.rowPosition = adapterPosition
                    // ResultFragment로 변경한다.
                    mainActivity.replaceFragment(FragmentName.FRAGMENT_RESULT, true, true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHilderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHilderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowBinding.root.layoutParams = params

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return mainActivity.studentList.size
        }

        override fun onBindViewHolder(holder: MainViewHilderClass, position: Int) {
            holder.textViewRow.text = mainActivity.studentList[position].name
        }
    }
}