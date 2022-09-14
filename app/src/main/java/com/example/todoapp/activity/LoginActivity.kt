package com.example.todoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var dataBinding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        setClick()
    }

    private fun setClick() {
        dataBinding.btnLogin.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        dataBinding.tvSignUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }
}