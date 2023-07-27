package com.test.mini02_boardproject01

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.test.mini02_boardproject01.databinding.FragmentPostWriteBinding

class PostWriteFragment : Fragment() {

    lateinit var fragmentPostWriteBinding: FragmentPostWriteBinding
    lateinit var mainActivity: MainActivity

    lateinit var albumActivityLauncher : ActivityResultLauncher<Intent>

    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostWriteBinding = FragmentPostWriteBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 권한 확인
        mainActivity.requestPermissions(permissionList, 0)

        val contract1 = ActivityResultContracts.StartActivityForResult()
        albumActivityLauncher = registerForActivityResult(contract1){

            if(it?.resultCode == RESULT_OK){

                // 선택한 이미지에 접근할 수 있는 Uri 객체를 추출한다.
                val uri = it.data?.data

                if (uri != null){
                    // 안드로이드 10(Q) 이상이라면
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

                        // 이미지를 생성할 수 있는 디코더를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)

                        // Bitmap객체를 생성한다.
                        val bitmap = ImageDecoder.decodeBitmap(source)

                        // 메인 화면의 imageView에 해당 이미지를 설정한다.
                        fragmentPostWriteBinding.imageViewPostWrite.setImageBitmap(bitmap)

                    } else {
                        // Content Provider를 통해 이미지 데이터 정보를 가져온다.
                        val cursor = mainActivity.contentResolver.query(uri,null,null,null,null)

                        if(cursor != null) {
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성하여 보여준다.
                            val bitmap = BitmapFactory.decodeFile(source)
                            fragmentPostWriteBinding.imageViewPostWrite.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }


        fragmentPostWriteBinding.run {
            toolbarPostWrtie.run {
                inflateMenu(R.menu.menu_write)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.item_post_write_photo -> {

                        }
                        R.id.item_post_write_album -> {
                            // 앨범에서 사진을 선택할 수 있는 Activity를 실행한다.
                            val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                            // 실행할 액티비티의 MIME 타입 설정(이미지로 설정)
                            newIntent.setType("image/*")

                            // 선택할 파일의 타입을 지정(안드로이드 os가 이미지에 대한 사진 작업을 할 수 있도록)
                            val mineType = arrayOf("image/*")
                            newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mineType)

                            // 액티비티 실행
                            albumActivityLauncher.launch(newIntent)
                        }
                        R.id.item_post_write_save -> {
                            mainActivity.removeFragment(MainActivity.POST_WRITE_FRAGMENT)
                        }
                    }

                    false
                }
            }
        }

        return fragmentPostWriteBinding.root
    }
}