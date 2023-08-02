package com.test.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelTest1  : ViewModel() {
    var data1 = MutableLiveData<String>()
    var data2 = MutableLiveData<String>()
}