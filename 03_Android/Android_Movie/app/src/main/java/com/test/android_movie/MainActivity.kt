package com.test.android_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import com.test.android_movie.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var imm : InputMethodManager

    val movieList = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        activityMainBinding.run {
            editTextTitle.requestFocus()
            thread {
                SystemClock.sleep(500)
                imm.showSoftInput(currentFocus, 0)
            }

            editTextDirectorName.run {
                setOnEditorActionListener { textView, i, keyEvent ->
                    val title = editTextTitle.text.toString()
                    val price = seekBar.progress.toString()
                    var age = ""
                    when(chipGroup.checkedChipId){
                        R.id.chipAll -> age = "전체"
                        R.id.chip12 -> age = "12세"
                        R.id.chip15 -> age = "15세"
                        R.id.chipAdult -> age = "성인"
                    }
                    val star = ratingBar.progress.toString()
                    val director = editTextDirectorName.text.toString()

                    val movie = Movie(title, price, age, star, director)
                    movieList.add(movie)

                    editTextTitle.setText("")
                    seekBar.progress = 5000
                    chipGroup.check(R.id.chipAll)
                    ratingBar.progress = 3
                    editTextDirectorName.setText("")

                    editTextTitle.requestFocus()

                    false
                }
            }

            seekBar.run {
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        textView.text = "가격 : ${progress}"
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }

                })
            }

            ratingBar.run{
                setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    textView2.text = "평점 : ${rating}"
                }
            }

            button.run {
                setOnClickListener {
                    if(currentFocus != null){
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        currentFocus!!.clearFocus()
                    }

                    textViewResult.text = ""

                    for (m in movieList){
                        textViewResult.append("제목 : ${m.title}\n")
                        textViewResult.append("가격 : ${m.price}원\n")
                        textViewResult.append("관람 등급 : ${m.age}\n")
                        textViewResult.append("평점 : ${m.star}점\n")
                        textViewResult.append("감독 : ${m.director}\n\n")
                    }
                }
            }
        }
    }
}

data class Movie(var title:String, var price:String, var age:String, var star:String, var director:String)