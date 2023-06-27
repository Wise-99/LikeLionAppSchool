package com.test.android_fragmentex02

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android_fragmentex02.databinding.FragmentMainBinding
import com.test.android_fragmentex02.databinding.MainRowBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    val playerType = arrayOf(
        "전체 선택", "야구부", "축구부", "수영부"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)

        fragmentMainBinding.run {
            spinnerMain.run {
                val spinnerAdapter = ArrayAdapter<String>(
                    mainActivity, android.R.layout.simple_spinner_item, playerType
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = spinnerAdapter
                setSelection(0)

                onItemSelectedListener = object : OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        mainActivity.selectList.clear()

                        if (position != 0){
                            for(p in mainActivity.playerList){
                                if (playerType[position] == p.type){
                                    mainActivity.selectList.add(0, p)
                                }
                            }
                        } else {
                            for(p in mainActivity.playerList){
                                mainActivity.selectList.add(0, p)
                            }
                        }

                        RecyclerViewMain.adapter?.notifyDataSetChanged()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }

            buttonMainToInput.setOnClickListener {
                mainActivity.replaceFragment(FragmentName.FRAGMENT_INPUT, true, true)
            }

            RecyclerViewMain.run {
                adapter = RecyclerViewAdapterClass()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL))
            }
        }

        return fragmentMainBinding.root
    }

    inner class RecyclerViewAdapterClass : RecyclerView.Adapter<RecyclerViewAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(mainRowBinding: MainRowBinding) : RecyclerView.ViewHolder(mainRowBinding.root){
            var textViewRowName : TextView

            init {
                textViewRowName = mainRowBinding.textViewRowName

                mainRowBinding.root.setOnClickListener {
                    mainActivity.rowPosition = adapterPosition
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
            return mainActivity.selectList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowName.text = mainActivity.selectList[position].name
        }
    }

    override fun onResume() {
        super.onResume()

        fragmentMainBinding.RecyclerViewMain.adapter?.notifyDataSetChanged()
    }
}