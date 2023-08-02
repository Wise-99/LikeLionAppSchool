package com.test.mini02_boardproject01.repository

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.test.mini02_boardproject01.MainActivity
import com.test.mini02_boardproject01.UserClass
import com.test.mini02_boardproject01.databinding.FragmentAddUserInfoBinding
import com.test.mini02_boardproject01.databinding.FragmentLoginBinding

class UserRepository {

    companion object {

        fun addUser(context: Context, joinUserId:String, joinUserPw:String, joinUserNickName:String, joinUserAge:String, binding:FragmentAddUserInfoBinding){
            var mainActivity = context as MainActivity

            // 사용자 인덱스 값을 가져온다.
            val database = FirebaseDatabase.getInstance()
            val userIdxRef = database.getReference("UserIdx")

            userIdxRef.get().addOnCompleteListener {
                // 현재의 사용자 순서 값을 가져온다.
                var userIdx = it.result.value as Long

                // 사용자 인덱스 번호 1 증가
                userIdx++

                val userClass = UserClass(userIdx, joinUserId, joinUserPw, joinUserNickName,
                    joinUserAge.toLong(),
                    binding.materialCheckBoxAddUserInfoHobby1.isChecked,
                    binding.materialCheckBoxAddUserInfoHobby2.isChecked,
                    binding.materialCheckBoxAddUserInfoHobby3.isChecked,
                    binding.materialCheckBoxAddUserInfoHobby4.isChecked,
                    binding.materialCheckBoxAddUserInfoHobby5.isChecked,
                    binding.materialCheckBoxAddUserInfoHobby6.isChecked
                )

                // 저장한다.
                val userDataRef = database.getReference("UserData")

                userDataRef.push().setValue(userClass).addOnCompleteListener {

                    userIdxRef.get().addOnCompleteListener {

                        // Log.d("abc", userIdx.toString())

                        it.result.ref.setValue(userIdx)

                        Snackbar.make(binding.root, "가입이 완료되었습니다", Snackbar.LENGTH_SHORT).show()

                        mainActivity.removeFragment(MainActivity.ADD_USER_INFO_FRAGMENT)
                        mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
                    }
                }
            }
        }

        fun login(context: Context, loginUserId:String, loginUserPw:String, binding : FragmentLoginBinding){
            val mainActivity = context as MainActivity

            val database = FirebaseDatabase.getInstance()
            val userDataRef = database.getReference("UserData")

            // userId가 사용자가 입력한 아이디와 같은 데이터를 가져온다.
            userDataRef.orderByChild("userId").equalTo(loginUserId).get().addOnCompleteListener {
                // 가져온 데이터가 없다면
                if(it.result.exists() == false){
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("로그인 오류")
                    builder.setMessage("존재하지 않는 아이디 입니다")
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        binding.textInputEditTextLoginUserId.setText("")
                        binding.textInputEditTextLoginUserPw.setText("")
                        mainActivity.showSoftInput(binding.textInputEditTextLoginUserId)
                    }
                    builder.show()
                }
                // 가져온 데이터가 있다면
                else {

                    for(c1 in it.result.children){
                        // 가져온 데이터에서 비밀번호를 가져온다.
                        val userPw = c1.child("userPw").value as String

                        // 입력한 비밀번호와 현재 계정의 비밀번호가 다르다면
                        if(loginUserPw != userPw){
                            val builder = MaterialAlertDialogBuilder(mainActivity)
                            builder.setTitle("로그인 오류")
                            builder.setMessage("잘못된 비밀번호 입니다")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                binding.textInputEditTextLoginUserPw.setText("")
                                mainActivity.showSoftInput(binding.textInputEditTextLoginUserPw)
                            }
                            builder.show()
                        }
                        // 입력한 비밀번호와 현재 계정의 비밀번호가 같다면
                        else {

                            // 로그인한 사용자 정보를 가져온다.
                            val userIdx = c1.child("userIdx").value as Long
                            val userId = c1.child("userId").value as String
                            val userPw = c1.child("userPw").value as String
                            val userNickname = c1.child("userNickname").value as String
                            val userAge = c1.child("userAge").value as Long
                            val hobby1 = c1.child("hobby1").value as Boolean
                            val hobby2 = c1.child("hobby2").value as Boolean
                            val hobby3 = c1.child("hobby3").value as Boolean
                            val hobby4 = c1.child("hobby4").value as Boolean
                            val hobby5 = c1.child("hobby5").value as Boolean
                            val hobby6 = c1.child("hobby6").value as Boolean

                            mainActivity.loginUserClass = UserClass(userIdx, userId, userPw, userNickname, userAge, hobby1, hobby2, hobby3, hobby4, hobby5, hobby6)
                            Snackbar.make(binding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                            mainActivity.replaceFragment(MainActivity.BOARD_MAIN_FRAGMENT, false, null)
                        }
                    }
                }
            }
        }

        fun idxToNickname(idx:Double) : String{
            var nickname = ""

            val database = FirebaseDatabase.getInstance()
            val userDataRef = database.getReference("UserData")

            // userId가 사용자가 입력한 아이디와 같은 데이터를 가져온다.
            userDataRef.orderByChild("userIdx").equalTo(idx).get().addOnCompleteListener {

                // 가져온 데이터가 없다면
                if(it.result.exists() == false){
                    nickname = "존재하지 않는 사용자"
                }
                // 가져온 데이터가 있다면
                else {
                    nickname = it.result.child("userNickname").value as String
                }
            }

            return nickname
        }
    }
}