package com.example.todoapp.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddUserBinding
import com.example.todoapp.model.UserModel
import com.example.todoapp.room.database.UserDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddUserFragment : Fragment() {
    lateinit var dataBinding: FragmentAddUserBinding
    private lateinit var userData: ArrayList<UserModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClick()

    }

    private fun setClick() {
        dataBinding.btnRegister.setOnClickListener {
            insertUserData()
        }
        setDate()
    }

    private fun insertUserData() {
        userData = ArrayList()
        val userModel = UserModel(
            0,
            dataBinding.teName.text.toString(),
            dataBinding.teEmail.text.toString(),
            dataBinding.teAge.text.toString(),
            dataBinding.teDob.text.toString()
        )
        userData.add(userModel)
        UserDatabase.getInstance(requireContext())?.userDao()!!.userInsert(userData)
        Toast.makeText(requireContext(), "Data Add Sucessfully", Toast.LENGTH_SHORT).show()
        clearData()
    }

    private fun clearData() {
        dataBinding.teName.text!!.clear()
        dataBinding.teEmail.text!!.clear()
        dataBinding.teAge.text!!.clear()
        dataBinding.teDob.text!!.clear()
    }

    //open calender
    private fun setDate() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate(myCalendar)
        }
        dataBinding.teDob.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    //set date format and show in edittext
    private fun updateDate(myCalender: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        dataBinding.teDob.setText(sdf.format(myCalender.time))
    }
}