package com.test.android_fragmentex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_fragmentex01.databinding.FragmentMainBinding
import com.test.android_fragmentex01.databinding.RowBinding

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
            buttonMain1.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.FRAGMENT_INPUT, true, true)
            }

            recyclerViewMain.run {
                adapter = RecyclerViewAdapterClass()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentMainBinding.root
    }

    inner class RecyclerViewAdapterClass : RecyclerView.Adapter<RecyclerViewAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            var textViewRowType : TextView
            var textViewRowName : TextView

            init{
                textViewRowType = rowBinding.textViewRowType
                textViewRowName = rowBinding.textViewRowName

                rowBinding.root.setOnClickListener {
                    mainActivity.animalPosition = adapterPosition
                    mainActivity.replaceFragment(FragmentName.FRAGMENT_RESULT,true,true)
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
            return mainActivity.animalList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowType.text = mainActivity.animalList[position].type
            holder.textViewRowName.text = mainActivity.animalList[position].name
        }
    }
}