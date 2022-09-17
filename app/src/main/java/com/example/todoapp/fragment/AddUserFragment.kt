package com.example.todoapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddUserBinding
import com.example.todoapp.model.UserModel
import com.example.todoapp.room.database.UserDatabase

class AddUserFragment : Fragment() {
    lateinit var dataBinding:FragmentAddUserBinding
    lateinit var userData:ArrayList<UserModel>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_add_user,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClick()
    }

    private fun setClick() {
        dataBinding.btnRegister.setOnClickListener{
            insertUserData()
        }
    }

    private fun insertUserData() {
        userData=ArrayList()
        val userModel = UserModel(0,
            dataBinding.teName.text.toString(),
            dataBinding.teEmail.text.toString(),
            dataBinding.teAge.text.toString(),
            dataBinding.teDob.text.toString()
        )
        userData.add(userModel)
        UserDatabase.getInstance(requireContext())?.userDao()!!.userInsert(userData)
    }
}