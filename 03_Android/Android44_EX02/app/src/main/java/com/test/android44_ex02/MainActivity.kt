package com.test.android44_ex02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android44_ex02.databinding.ActivityMainBinding
import com.test.android44_ex02.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding : ActivityMainBinding
    lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>

    val dataList = ArrayList<FruitClass>()            // 데이터를 담을 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val c1 = ActivityResultContracts.StartActivityForResult()
        addActivityResultLauncher = registerForActivityResult(c1){

            if (it.resultCode == RESULT_OK){
                val name = it.data?.getStringExtra("name")
                val number = it.data?.getIntExtra("number", 0)
                val country = it.data?.getStringExtra("country")

                val f1 = FruitClass()
                f1.name = name!!
                f1.number = number!!
                f1.country = country!!

                dataList.add(f1)

                val adapter = activityMainBinding.recyclerViewResult.adapter as RecyclerAdapterClass
                adapter.notifyDataSetChanged()
            }
        }

        activityMainBinding.run {
            recyclerViewResult.run {
                adapter = RecyclerAdapterClass()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_manu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuAdd -> {
                val addIntent = Intent(this@MainActivity, AddActivity::class.java)
                addActivityResultLauncher.launch(addIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class RecyclerAdapterClass : RecyclerView.Adapter<RecyclerAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(rowBinding : RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            val textViewRowName = rowBinding.textViewRowName
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
            return dataList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRowName.text = "${dataList[position].name}"

            holder.textViewRowName.setOnClickListener {
                val showIntent = Intent(this@MainActivity, ShowActivity::class.java)
                showIntent.putExtra("show", dataList[position])
                startActivity(showIntent)
            }
        }
    }
}

class FruitClass() : Parcelable{

    var name = ""
    var number = 0
    var country = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()!!
        number = parcel.readInt()
        country = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(number)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FruitClass> {
        override fun createFromParcel(parcel: Parcel): FruitClass {
            return FruitClass(parcel)
        }

        override fun newArray(size: Int): Array<FruitClass?> {
            return arrayOfNulls(size)
        }
    }
}