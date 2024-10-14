package com.alviano.fetch.api.android.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alviano.fetch.api.android.data.Person
import com.alviano.fetch.api.android.databinding.MyViewHolderBinding

class MyAdapters(private val dataList: List<Person>) :
    RecyclerView.Adapter<MyAdapters.MyViewHolder>() {

    class MyViewHolder(val binding: MyViewHolderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val binding = MyViewHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =dataList[position]
        holder.binding.idTxt.text = currentItem.id.toString()
        holder.binding.emailTxt.text = currentItem.email
        holder.binding.firstNameTxt.text = currentItem.firstName
        holder.binding.lastNameTxt.text = currentItem.lastName
    }
}