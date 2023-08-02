package com.test.mini02_boardproject01.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mini02_boardproject01.repository.BoardRepository

class ViewModelBoard : ViewModel() {
    var postList = MutableLiveData<MutableList<PostDataClass>>()

    init {
        getAll()
    }

    fun getAll(){
        // Repository로 부터 데이터를 가져와 설정한다.
        postList.value = BoardRepository.getAll()
    }
}

// 게시글 정보를 담을 클래스
data class PostDataClass(var postIdx:Long,              // 게시글 인덱스 번호
                         var postType:Long,             // 게시판 종류
                         var postSubject:String,        // 제목
                         var postText:String,           // 내용
                         var postWriteDate:String,      // 작성일
                         var postImage:String,          // 첨부이미지 파일 이름
                         var postWriterIdx:Long)        // 작성자 인덱스 번호