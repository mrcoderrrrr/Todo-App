package com.example.todoapp.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Database
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRegisterBinding
import com.example.todoapp.model.RegisterModel
import com.example.todoapp.model.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.ref.Reference
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityRegisterBinding
    lateinit var registerData:ArrayList<RegisterModel>
    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseDatabase: FirebaseDatabase? =null
    private var databaseReference: DatabaseReference? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        firebaseDatabase=FirebaseDatabase.getInstance()
        setClick()
    }

    private fun setClick() {
        dataBinding.btnRegister.setOnClickListener {
            //if (validate() && passwordvalidate()) {
                firebaseRegister()
                firebaseStoreData()

        }

        setDate()
    }



    private fun firebaseStoreData() {
        registerData=ArrayList()
        val registerModel= RegisterModel(
            dataBinding.teName.text.toString(),
            dataBinding.teEmail.text.toString(),
            dataBinding.teMobileNo.toString(),
            dataBinding.teAge.text.toString(),
            dataBinding.teDob.text.toString()
        )
        registerData.add(registerModel)

        databaseReference= firebaseDatabase!!.getReference("TableNotes").child("UserData").child(dataBinding.teMobileNo.text.toString())
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                databaseReference!!.setValue(registerModel)
                Toast.makeText(this@RegisterActivity,"Data Submit Sucessfully",Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterActivity,"Failed  to submit data",Toast.LENGTH_SHORT).show()
            }
        })
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
            dataBinding.teMobileNo.text!!.isEmpty()&&
            dataBinding.teAge.text!!.isEmpty() &&
            dataBinding.teDob.text!!.isEmpty() &&
            dataBinding.tePassword.text!!.isEmpty() &&
            dataBinding.teCPassword.text!!.isEmpty()
        ) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    //password validation
    private fun passwordvalidate(): Boolean {
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
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                    firebaseAuth.signOut()
                    Toast.makeText(this, "Register Sucessful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

}