package com.test.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelTest2 : ViewModel(){
    var dataList = MutableLiveData<MutableList<TestData>>()
    val tempList = mutableListOf<TestData>()

    init {
        dataList.value = tempList
    }

    fun addItem(testData: TestData){
        tempList.add(testData)
        dataList.value = tempList
    }
}

data class TestData(var data1:String, var data2:String)