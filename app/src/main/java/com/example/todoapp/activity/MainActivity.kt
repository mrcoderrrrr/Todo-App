package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.fragment.AddUserFragment
import com.example.todoapp.fragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityMainBinding
    var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain, HomeFragment()).commit()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        userLogin()
        setClick()
    }

    private fun setClick() {
        dataBinding.fbAdd.setOnClickListener {
            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction().replace(R.id.flMain,AddUserFragment())
            fragmentTransaction.commit()
        }
    }

    private fun userLogin() {
        val firebaseUser = firebaseAuth!!.currentUser
        //if user is note null then open module activity
        if (firebaseUser != null) {
            Log.d("User", firebaseUser.toString())
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater().inflate(R.menu.item_menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                firebaseLogout()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun firebaseLogout() {
        firebaseAuth!!.signOut()
        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}