package com.test.android_httpjson_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android_httpjson_ex01.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    val serverAddress = "https://a.4cdn.org/boards.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run{
            button.setOnClickListener {
                thread {
                    // URL 객체 생성
                    val url = URL(serverAddress)
                    // 접속 후 스트림 추출
                    val httpURLConnection = url.openConnection() as HttpURLConnection

                    val inputStreamReader = InputStreamReader(httpURLConnection.inputStream, "UTF-8")
                    val bufferedReader = BufferedReader(inputStreamReader)

                    var str:String? = null
                    val stringBuffer = StringBuffer()
                    // 문서의 마지막까지 읽어온다.
                    do{
                        str = bufferedReader.readLine()
                        if(str != null){
                            stringBuffer.append(str)
                        }
                    }while(str != null)

                    val data = stringBuffer.toString()

                    // JSON 데이터 분석
                    // { } : JSONObject, 이름 - 값 형태
                    // [ ] :JSONArray, 0부터 1씩 증가하는 순서값을 가지고 관리
                    // 100 : 정수
                    // 11.11 : 실수
                    // "문자열" : 문자열
                    // true, false : 논리형

                    // 최상위 { } 이므로 JSONObject 생성
                    val root = JSONObject(data)

                    // boards
                    val boardsArray = root.getJSONArray("boards")

                    runOnUiThread {
                        textView.text = ""
                    }

                    for(idx in 0 until boardsArray.length()){
                        // idx 번째 JSONObject를 추출한다.
                        val boardsObject = boardsArray.getJSONObject(idx)
                        // board
                        val board = boardsObject.getString("board")
                        // title
                        val title = boardsObject.getString("title")
                        // pages
                        val pages = boardsObject.getInt("pages")
                        // image_limit
                        val imageLimit = boardsObject.getInt("image_limit")
                        // threads, replies, images
                        val cooldowns = boardsObject.getJSONObject("cooldowns")


                        runOnUiThread {
                            textView.append("board : ${board}\n")
                            textView.append("title : ${title}\n")
                            textView.append("pages : ${pages}\n")
                            textView.append("imageLimit : ${imageLimit}\n")
                            textView.append("threads : ${cooldowns.getInt("threads")}\n")
                            textView.append("replies : ${cooldowns.getInt("replies")}\n")
                            textView.append("images : ${cooldowns.getInt("images")}\n\n\n")
                        }
                    }
                }
            }
        }
    }
}