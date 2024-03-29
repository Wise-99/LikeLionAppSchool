package com.test.material10_navigationrail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigationrail.NavigationRailView
import com.test.material10_navigationrail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run{
            navigationRailView.run{

                // 라벨 표시 설정
                // labelVisibilityMode = NavigationRailView.LABEL_VISIBILITY_UNLABELED
                // labelVisibilityMode = NavigationRailView.LABEL_VISIBILITY_LABELED
                labelVisibilityMode = NavigationRailView.LABEL_VISIBILITY_SELECTED
                // labelVisibilityMode = NavigationRailView.LABEL_VISIBILITY_AUTO

                setOnItemSelectedListener {
                    when(it.itemId){
                        R.id.menu_item1 -> {
                            textView.text = "첫 번째 항목을 눌렀습니다"
                        }
                        R.id.menu_item2 -> {
                            textView.text = "두 번째 항목을 눌렀습니다"
                        }
                        R.id.menu_item3 -> {
                            textView.text = "세 번째 항목을 눌렀습니다"
                        }
                    }

                    true // false로 하면 이벤트는 받으나 선택 시 메뉴명이 뜨지 않음
                }

                // 만약 선택 메뉴를 변경하고자 한다면...
                selectedItemId = R.id.menu_item2
            }
        }
    }
}