package com.test.android56_ex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android56_ex01.databinding.FragmentMainBinding
import com.test.android56_ex01.databinding.MainRowBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentMainBinding.run {
            recyclerViewMain.run {
                adapter = MainRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL))
            }

            buttonMainAdd.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.FRAGMENT_INPUT, true, true)
            }
        }

        return fragmentMainBinding.root
    }

    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolderClass>(){
        inner class MainViewHolderClass(mainRowBinding: MainRowBinding) : RecyclerView.ViewHolder(mainRowBinding.root){
            var textViewMainRowName : TextView
            var textViewMainRowType : TextView

            init{
                textViewMainRowName = mainRowBinding.textViewMainRowName
                textViewMainRowType = mainRowBinding.textViewMainRowType

                mainRowBinding.root.setOnClickListener {
                    mainActivity.rowPosition = adapterPosition
                    mainActivity.replaceFragment(FragmentName.FRAGMENT_RESULT, true,  true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderClass {
            val mainRowBinding = MainRowBinding.inflate(layoutInflater)
            val mainViewHolderClass = MainViewHolderClass(mainRowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            mainRowBinding.root.layoutParams = params

            return mainViewHolderClass
        }

        override fun getItemCount(): Int {
            return mainActivity.animalList.size
        }

        override fun onBindViewHolder(holder: MainViewHolderClass, position: Int) {
            holder.textViewMainRowType.text = mainActivity.animalList[position].type
            holder.textViewMainRowName.text = mainActivity.animalList[position].name
        }
    }

    override fun onResume() {
        super.onResume()

        fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }
}