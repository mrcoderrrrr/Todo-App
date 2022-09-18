package com.example.todoapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.adapter.UserDataAdapter
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    lateinit var dataBinding: FragmentHomeBinding
    private var firebaseAuth: FirebaseAuth? =null
    private var firebaseDatabase: FirebaseDatabase? =null
    private var databaseReference: DatabaseReference? =null
    var registerData=ArrayList<RegisterModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        setClick()
        viewUserdata()
    }

    private fun viewUserdata() {
        getFirebaseData()
    }

    private fun getFirebaseData() {
        databaseReference= firebaseDatabase!!.getReference("TableNotes").child("UserData")
        databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                  Log.d("UserData", snapshot.toString())
                      val registerModel=RegisterModel(snapshot.child("name").value.toString(),
                          snapshot.child("email").value.toString(),
                          snapshot.child("mobileno").value.toString(),
                          snapshot.child("age").value.toString(),
                          snapshot.child("dob").value.toString())
                      //Personal Details
                      registerData.add(registerModel)
                val userDataAdapter=UserDataAdapter(requireContext(),registerData)
                dataBinding.rcvUserData.adapter=userDataAdapter
                userDataAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setClick() {

    }
}