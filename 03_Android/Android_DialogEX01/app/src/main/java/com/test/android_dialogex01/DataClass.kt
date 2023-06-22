package com.test.android_dialogex01

class DataClass {

    companion object{
        val personList= mutableListOf<PersonClass>()
    }

}

data class PersonClass(var name:String, var date:String, var gender:String, var hobby:String)