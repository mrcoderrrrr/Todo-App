package com.example.todoapp.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setClick()
    }

    private fun setClick() {
        dataBinding.btnRegister.setOnClickListener {
            if (validate()) {
                firebaseRegister()
            }
        }
        setDate()
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
                this,
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
//validation
    private fun validate(): Boolean {
        if (dataBinding.teEmail.text!!.isEmpty() &&
            dataBinding.teName.text!!.isEmpty() &&
            dataBinding.teAge.text!!.isEmpty() &&
            dataBinding.teDob.text!!.isEmpty() &&
            dataBinding.tePassword.text!!.isEmpty() &&
            dataBinding.teCPassword.text!!.isEmpty()
        ) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!dataBinding.tePassword.text!!.equals(dataBinding.teCPassword.text)){
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    //Firebase Register
    private fun firebaseRegister() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(
            dataBinding.teEmail.text.toString(),
            dataBinding.teCPassword.text.toString()
        ).addOnCompleteListener(this,
            OnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    Toast.makeText(this, "Register Sucessful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            })
    }
}