package com.test.android45_ex01

class DataClass {

    companion object{
        // 과일 정보를 담을 클래스
        val fruitList = mutableListOf<FruitClass>()
    }
}

data class FruitClass(var type:String, var volume:Int, var region:String)