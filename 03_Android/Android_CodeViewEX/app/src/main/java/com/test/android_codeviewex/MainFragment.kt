package com.test.android_codeviewex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_codeviewex.databinding.FragmentMainBinding
import com.test.android_codeviewex.databinding.MainRowBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding : FragmentMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity

        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)

        fragmentMainBinding.run {

            recyclerViewMain.run {
                adapter = RecyclerViewAdapterClass()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentMainBinding.root
    }

    inner class RecyclerViewAdapterClass : RecyclerView.Adapter<RecyclerViewAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(mainRowBinding: MainRowBinding) : RecyclerView.ViewHolder(mainRowBinding.root){
            var textViewRowName : TextView

            init{
                textViewRowName = mainRowBinding.textViewRowName

                mainRowBinding.root.setOnClickListener {
                    mainActivity.personPosition = adapterPosition
                    mainActivity.replaceFragment(FragmentName.FRAGMENT_SHOW,true,true)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val mainRowBinding = MainRowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(mainRowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            mainRowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return mainActivity.personList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowName.text = mainActivity.personList[position].name
        }
    }
}