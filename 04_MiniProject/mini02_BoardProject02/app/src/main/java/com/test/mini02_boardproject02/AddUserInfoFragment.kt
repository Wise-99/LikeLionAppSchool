package com.test.mini02_boardproject02

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.test.mini02_boardproject02.databinding.FragmentAddUserInfoBinding
import com.test.mini02_boardproject02.repository.UserRepository

class AddUserInfoFragment : Fragment() {

    lateinit var fragmentAddUserInfoBinding: FragmentAddUserInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddUserInfoBinding = FragmentAddUserInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentAddUserInfoBinding.run{
            // toolbar
            toolbarAddUserInfo.run{
                title = "회원가입"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ADD_USER_INFO_FRAGMENT)
                }
            }
            // 가입 완료 버튼
            buttonAddUserInfoSubmit.run{
                setOnClickListener {
                    // 입력한 내용을 가져온다.
                    val joinUserNickName = textInputEditTextAddUserInfoNickName.text.toString()
                    val joinUserAge = textInputEditTextAddUserInfoAge.text.toString()

                    if(joinUserNickName.isEmpty()){
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("닉네임 입력 오류")
                        builder.setMessage("닉네임을 입력해주세요")
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            mainActivity.showSoftInput(textInputEditTextAddUserInfoNickName)
                        }
                        builder.show()
                        return@setOnClickListener
                    }

                    if(joinUserAge.isEmpty()){
                        val builder = MaterialAlertDialogBuilder(mainActivity)
                        builder.setTitle("나이 입력 오류")
                        builder.setMessage("나이를 입력해주세요")
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            mainActivity.showSoftInput(textInputEditTextAddUserInfoAge)
                        }
                        builder.show()
                        return@setOnClickListener
                    }

                    // 사용자 인덱스 값을 가져온다.
                    UserRepository.getUserIdx {
                        // 현재의 사용자 순서값을 가져온다.
                        var userIdx = it.result.value as Long

                        // 저장할 데이터들을 담는다.
                        val joinUserId = arguments?.getString("joinUserId")!!
                        val joinUserPw = arguments?.getString("joinUserPw")!!

                        // 사용자 인덱스 번호 1 증가
                        userIdx++

                        val userClass = UserClass(userIdx, joinUserId, joinUserPw, joinUserNickName,
                            joinUserAge.toLong(),
                            materialCheckBoxAddUserInfoHobby1.isChecked,
                            materialCheckBoxAddUserInfoHobby2.isChecked,
                            materialCheckBoxAddUserInfoHobby3.isChecked,
                            materialCheckBoxAddUserInfoHobby4.isChecked,
                            materialCheckBoxAddUserInfoHobby5.isChecked,
                            materialCheckBoxAddUserInfoHobby6.isChecked
                        )

                        // 저장한다.
                        UserRepository.addUserInfo(userClass){
                            UserRepository.setUserIdx(userIdx) {
                                Snackbar.make(fragmentAddUserInfoBinding.root, "가입이 완료되었습니다", Snackbar.LENGTH_SHORT).show()

                                mainActivity.removeFragment(MainActivity.ADD_USER_INFO_FRAGMENT)
                                mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
                            }
                        }
                    }
                }
            }

            // 취미 전체 체크박스
            materialCheckBoxAddUserInfoAll.run{
                setOnCheckedChangeListener { compoundButton, b ->
                    // 각 체크박스를 가지고 있는 레이아웃을 통해 그 안에 있는 View들의 체크상태를 변경한다.
                    for(v1 in materialCheckBoxGroupUserInfo1.children){
                        // 형변환
                        v1 as MaterialCheckBox
                        // 취미 전체가 체크 되어 있다면
                        if(b){
                            v1.checkedState = MaterialCheckBox.STATE_CHECKED
                        } else {
                            v1.checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                    }

                    for(v1 in materialCheckBoxGroupUserInfo2.children){
                        // 형변환
                        v1 as MaterialCheckBox
                        // 취미 전체가 체크 되어 있다면
                        if(b){
                            v1.checkedState = MaterialCheckBox.STATE_CHECKED
                        } else {
                            v1.checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                    }
                }
            }

            // 다른 체크박스 들...
            // 반복문
            for(v1 in materialCheckBoxGroupUserInfo1.children){
                v1 as MaterialCheckBox
                v1.setOnCheckedChangeListener { compoundButton, b ->
                    setParentCheckBoxState()
                }
            }
            for(v1 in materialCheckBoxGroupUserInfo2.children){
                v1 as MaterialCheckBox
                v1.setOnCheckedChangeListener { compoundButton, b ->
                    setParentCheckBoxState()
                }
            }

            // 닉네임에 포커스를 주고 키보드를 올려준다.
            mainActivity.showSoftInput(textInputEditTextAddUserInfoNickName)
        }

        return fragmentAddUserInfoBinding.root
    }

    // 하위의 체크박스들의 상태를 보고 상위 체크 박스 상태를 셋팅한다
    fun setParentCheckBoxState(){
        fragmentAddUserInfoBinding.run{

            // 체크박스의 개수를 구한다.
            val checkBoxCount = materialCheckBoxGroupUserInfo1.childCount + materialCheckBoxGroupUserInfo2.childCount
            // 체크되어 있는 체크박스의 개수
            var checkedCount = 0

            // 체크 되어 있는 체크박스의 개수를 구한다.
            for(v1 in materialCheckBoxGroupUserInfo1.children){
                v1 as MaterialCheckBox
                if(v1.checkedState == MaterialCheckBox.STATE_CHECKED){
                    checkedCount++
                }
            }
            for(v1 in materialCheckBoxGroupUserInfo2.children){
                v1 as MaterialCheckBox
                if(v1.checkedState == MaterialCheckBox.STATE_CHECKED){
                    checkedCount++
                }
            }

            // 만약 체크되어 있는 것이 없다면
            if(checkedCount == 0){
                materialCheckBoxAddUserInfoAll.checkedState = MaterialCheckBox.STATE_UNCHECKED
            }
            // 모두 체크되어 있다면
            else if(checkedCount == checkBoxCount){
                materialCheckBoxAddUserInfoAll.checkedState = MaterialCheckBox.STATE_CHECKED
            }
            // 일부만 체크되어 있다면
            else {
                materialCheckBoxAddUserInfoAll.checkedState = MaterialCheckBox.STATE_INDETERMINATE
            }
        }
    }
}