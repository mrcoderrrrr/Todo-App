package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var dataBinding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        firebaseAuth=FirebaseAuth.getInstance()
        setClick()
    }
    private fun setClick() {
        dataBinding.btnLogin.setOnClickListener{
            if(isLogin()) {
                firebaseLogin()
            }
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
        }
        dataBinding.tvSignUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }

    private fun isLogin(): Boolean {
        if(dataBinding.teEmail.text!!.isEmpty() && dataBinding.tePassword.text!!.isEmpty()) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            return false
        }
    return true
    }

    //login
    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(dataBinding.teEmail.text.toString(),dataBinding.tePassword.text.toString()).addOnCompleteListener( this,
            OnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Sucessful",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
                }
            })
    }
}