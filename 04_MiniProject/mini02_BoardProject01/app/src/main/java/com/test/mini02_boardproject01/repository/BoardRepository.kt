package com.test.mini02_boardproject01.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.test.mini02_boardproject01.MainActivity
import com.test.mini02_boardproject01.databinding.FragmentPostWriteBinding
import com.test.mini02_boardproject01.vm.PostDataClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoardRepository {

    companion object{

        fun addPost(context: Context, uploadUri:Uri, boardType:Int, subject:String, text:String, binding:FragmentPostWriteBinding){
            val mainActivity = context as MainActivity

            val database = FirebaseDatabase.getInstance()
            // 게시글 인덱스 번호
            val postIdxRef = database.getReference("PostIdx")
            postIdxRef.get().addOnCompleteListener {
                var postIdx = it.result.value as Long
                // 게시글 인덱스를 증가한다.
                postIdx++

                // 게시글을 저장한다.
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val writeDate = sdf.format(Date(System.currentTimeMillis()))

                val fileName = if(uploadUri == null){
                    "None"
                } else {
                    "image/img_${System.currentTimeMillis()}.jpg"
                }

                val postDataClass = PostDataClass(postIdx, boardType.toLong(), subject,
                    text, writeDate, fileName, mainActivity.loginUserClass.userIdx)

                val postDataRef = database.getReference("PostData")
                postDataRef.push().setValue(postDataClass).addOnCompleteListener {
                    // 게시글 인덱스번호 저장
                    postIdxRef.get().addOnCompleteListener {
                        it.result.ref.setValue(postIdx).addOnCompleteListener {
                            // 이미지 업로드
                            if(uploadUri != null){
                                val storage = FirebaseStorage.getInstance()
                                val imageRef = storage.reference.child(fileName)
                                imageRef.putFile(uploadUri!!).addOnCompleteListener{
                                    Snackbar.make(binding.root, "저장되었습니다!", Snackbar.LENGTH_SHORT).show()
                                    mainActivity.replaceFragment(MainActivity.POST_READ_FRAGMENT, true, null)
                                }
                            } else {
                                Snackbar.make(binding.root, "저장되었습니다", Snackbar.LENGTH_SHORT).show()
                                mainActivity.replaceFragment(MainActivity.POST_READ_FRAGMENT, true, null)
                            }
                        }
                    }
                }
            }
        }

        fun getAll() : MutableList<PostDataClass>{
            val tempList = mutableListOf<PostDataClass>()

            val database = FirebaseDatabase.getInstance()
            val postDataRef = database.getReference("PostData")

            postDataRef.get().addOnCompleteListener {

                if(it.result.exists() == true){
                    for(c1 in it.result.children) {

                        val postIdx = c1.child("postIdx").value as Long
                        val postType = c1.child("postType").value as Long
                        val postSubject = c1.child("postSubject").value as String
                        val postText = c1.child("postText").value as String
                        val postWriteDate = c1.child("postWriteDate").value as String
                        val postImage = c1.child("postImage").value as String
                        val postWriterIdx = c1.child("postWriterIdx").value as Long

                        val postDataClass = PostDataClass(postIdx, postType, postSubject, postText, postWriteDate, postImage, postWriterIdx)

                        tempList.add(postDataClass)
                    }
                }
                tempList
            }
            return tempList
        }
    }
}