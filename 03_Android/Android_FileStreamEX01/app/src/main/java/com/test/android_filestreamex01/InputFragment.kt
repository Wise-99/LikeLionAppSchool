package com.test.android_filestreamex01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.test.android_filestreamex01.databinding.FragmentInputBinding
import java.io.DataInputStream
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)

        fragmentInputBinding.run {

            mainActivity.readObject()

            buttonInputSave.setOnClickListener {
                val fos = mainActivity.openFileOutput("data.dat", AppCompatActivity.MODE_PRIVATE)
                val oos = ObjectOutputStream(fos)

                for (p in mainActivity.personList){
                    oos.writeObject(p)
                }

                val name = editTextInputName.text.toString()
                val age = editTextInputAge.text.toString().toInt()
                val korean = editTextInputKorean.text.toString().toInt()

                val person = PersonClass(name, age, korean)
                oos.writeObject(person)

                oos.flush()
                oos.close()

                mainActivity.removeFragment(FragmentName.FRAGMENT_INPUT)
            }
        }

        return fragmentInputBinding.root
    }
}