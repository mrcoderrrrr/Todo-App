package com.example.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemUserListBinding
import com.example.todoapp.model.RegisterModel

class UserDataAdapter(val context: Context, val userData: ArrayList<RegisterModel>):RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var dataBinding:ItemUserListBinding=ItemUserListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.tvNameTxt.text=userData[position].name
        holder.dataBinding.tvAgeTxt.text=userData[position].age
        holder.dataBinding.tvDobTxt.text=userData[position].dob
    }
}